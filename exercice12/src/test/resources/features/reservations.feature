# language: fr
Fonctionnalité: Réservation de salles de réunion
  En tant qu'utilisateur
  Je veux réserver une salle sur un créneau
  Afin d'organiser mes réunions sans conflit

  Scénario: Réservation acceptée quand la salle existe et que le créneau est libre
    Étant donné une salle "Salle A" de capacité 6
    Quand on réserve la salle de "2026-06-12T09:00:00" à "2026-06-12T10:00:00" pour "Alice"
    Alors la réservation est confirmée

  Scénario: Réservation refusée quand la salle n'existe pas
    Étant donné aucune salle d'identifiant 9999
    Quand on réserve la salle de "2026-06-12T09:00:00" à "2026-06-12T10:00:00" pour "Alice"
    Alors la réservation est refusée car la salle n'existe pas

  Scénario: Réservation refusée quand le créneau chevauche une réservation existante
    Étant donné une salle "Salle B" de capacité 4
    Et une réservation confirmée de "2026-06-12T09:30:00" à "2026-06-12T10:30:00" pour cette salle
    Quand on réserve la salle de "2026-06-12T09:00:00" à "2026-06-12T10:00:00" pour "Alice"
    Alors la réservation est refusée car le créneau chevauche une réservation existante
