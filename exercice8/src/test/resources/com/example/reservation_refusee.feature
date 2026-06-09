# language: fr
Fonctionnalité: Réservation refusée
  En tant qu'entreprise
  Je veux refuser les réservations invalides
  Afin de garantir la cohérence des créneaux et des capacités

  Scénario: La réservation est refusée si la salle est inconnue
    Étant donné que la salle "S-INCONNUE" n'existe pas
    Quand un utilisateur réserve la salle "S-INCONNUE" pour 4 participants du "2026-06-10 09:00" au "2026-06-10 10:00"
    Alors la réservation est refusée car la salle est inconnue

  Scénario: La réservation est refusée si la capacité est insuffisante
    Étant donné une salle "S1" nommée "Alpha" d'une capacité de 5
    Et aucune réservation existante pour la salle "S1"
    Quand un utilisateur réserve la salle "S1" pour 8 participants du "2026-06-10 09:00" au "2026-06-10 10:00"
    Alors la réservation est refusée car la capacité est insuffisante

  Scénario: La réservation est refusée si la période est invalide
    Étant donné une salle "S1" nommée "Alpha" d'une capacité de 10
    Et aucune réservation existante pour la salle "S1"
    Quand un utilisateur réserve la salle "S1" pour 4 participants du "2026-06-10 10:00" au "2026-06-10 09:00"
    Alors la réservation est refusée car la période est invalide

  Scénario: La réservation est refusée en cas de conflit avec une réservation existante
    Étant donné une salle "S1" nommée "Alpha" d'une capacité de 10
    Et une réservation existante pour la salle "S1" du "2026-06-10 09:00" au "2026-06-10 11:00"
    Quand un utilisateur réserve la salle "S1" pour 4 participants du "2026-06-10 10:00" au "2026-06-10 12:00"
    Alors la réservation est refusée car la salle est déjà réservée
