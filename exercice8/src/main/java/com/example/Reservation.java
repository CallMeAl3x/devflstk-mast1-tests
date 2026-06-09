package com.example;

import java.time.LocalDateTime;

public record Reservation(String emailUtilisateur, String codeSalle, int nombreParticipants,
                          LocalDateTime dateDebut, LocalDateTime dateFin) {

    /**
     * @return true si cette réservation chevauche l'intervalle de temps de {@code autre}.
     *         Deux créneaux qui se touchent simplement (fin de l'un = début de l'autre)
     *         ne sont pas considérés en conflit.
     */
    public boolean chevauche(Reservation autre) {
        return dateDebut.isBefore(autre.dateFin) && autre.dateDebut.isBefore(dateFin);
    }
}
