package com.example.bdd;

import com.example.exception.ReservationConflictException;
import com.example.exception.RoomNotFoundException;
import com.example.model.Reservation;
import com.example.model.Room;
import com.example.service.ReservationService;
import com.example.service.RoomService;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/** Définitions des étapes Gherkin, branchées sur les vrais services Spring. */
public class ReservationStepDefinitions {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    private Long roomId;
    private Reservation reservation;
    private RuntimeException erreur;

    @Etantdonné("une salle {string} de capacité {int}")
    public void uneSalle(String nom, int capacite) {
        Room room = roomService.create(nom, capacite);
        roomId = room.getId();
    }

    @Etantdonné("aucune salle d'identifiant {long}")
    public void aucuneSalle(Long id) {
        roomId = id;
    }

    @Etantdonné("une réservation confirmée de {string} à {string} pour cette salle")
    public void uneReservationConfirmee(String debut, String fin) {
        reservationService.create(roomId, "Bob", LocalDateTime.parse(debut), LocalDateTime.parse(fin));
    }

    @Quand("on réserve la salle de {string} à {string} pour {string}")
    public void onReserve(String debut, String fin, String personne) {
        try {
            reservation = reservationService.create(
                    roomId, personne, LocalDateTime.parse(debut), LocalDateTime.parse(fin));
        } catch (RoomNotFoundException | ReservationConflictException e) {
            erreur = e;
        }
    }

    @Alors("la réservation est confirmée")
    public void laReservationEstConfirmee() {
        assertThat(erreur).isNull();
        assertThat(reservation).isNotNull();
        assertThat(reservation.getStatus().name()).isEqualTo("CONFIRMED");
    }

    @Alors("la réservation est refusée car la salle n'existe pas")
    public void refuseeSalleInexistante() {
        assertThat(erreur).isInstanceOf(RoomNotFoundException.class);
    }

    @Alors("la réservation est refusée car le créneau chevauche une réservation existante")
    public void refuseeChevauchement() {
        assertThat(erreur).isInstanceOf(ReservationConflictException.class);
    }
}
