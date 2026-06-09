package com.example;

import java.util.List;

public class ServiceReservation {

    private final RepertoireSalle repertoire;
    private final RegistreReservation registre;
    private final ServiceNotification notification;

    public ServiceReservation(RepertoireSalle repertoire, RegistreReservation registre,
                              ServiceNotification notification) {
        this.repertoire = repertoire;
        this.registre = registre;
        this.notification = notification;
    }

    public ConfirmationReservation reserver(Reservation demande) {
        Salle salle = repertoire.trouverParCode(demande.codeSalle());
        if (salle == null) {
            throw new SalleInconnueException(demande.codeSalle());
        }
        if (!demande.dateFin().isAfter(demande.dateDebut())) {
            throw new PeriodeInvalideException();
        }
        if (demande.nombreParticipants() > salle.capaciteMax()) {
            throw new CapaciteInsuffisanteException(
                    salle.code(), demande.nombreParticipants(), salle.capaciteMax());
        }

        List<Reservation> existantes = registre.reservationsPour(demande.codeSalle());
        boolean conflit = existantes.stream().anyMatch(demande::chevauche);
        if (conflit) {
            throw new ConflitReservationException(demande.codeSalle());
        }

        notification.envoyerConfirmation(demande);
        return new ConfirmationReservation(
                salle.code(),
                demande.emailUtilisateur(),
                "Réservation confirmée pour la salle " + salle.nom());
    }
}
