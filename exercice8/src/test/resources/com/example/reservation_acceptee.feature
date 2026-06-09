# language: fr
Fonctionnalité: Réservation acceptée
  En tant qu'employé
  Je veux réserver une salle de réunion disponible
  Afin d'organiser une réunion sur un créneau valide

  Scénario: Une réservation valide est acceptée
    Étant donné une salle "S1" nommée "Alpha" d'une capacité de 10
    Et aucune réservation existante pour la salle "S1"
    Quand un utilisateur réserve la salle "S1" pour 4 participants du "2026-06-10 09:00" au "2026-06-10 10:00"
    Alors la réservation est acceptée

  Scénario: Une réservation à capacité maximale est acceptée
    Étant donné une salle "S1" nommée "Alpha" d'une capacité de 10
    Et aucune réservation existante pour la salle "S1"
    Quand un utilisateur réserve la salle "S1" pour 10 participants du "2026-06-10 09:00" au "2026-06-10 10:00"
    Alors la réservation est acceptée

  Scénario: Une réservation dont le créneau commence après une réservation existante est acceptée
    Étant donné une salle "S1" nommée "Alpha" d'une capacité de 10
    Et une réservation existante pour la salle "S1" du "2026-06-10 09:00" au "2026-06-10 10:00"
    Quand un utilisateur réserve la salle "S1" pour 4 participants du "2026-06-10 10:00" au "2026-06-10 11:00"
    Alors la réservation est acceptée
