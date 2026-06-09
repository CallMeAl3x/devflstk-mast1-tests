# language: fr
Fonctionnalité: Notification de réservation
  En tant qu'utilisateur
  Je veux être notifié uniquement lorsque ma réservation aboutit
  Afin de savoir si mon créneau est confirmé

  Scénario: Une confirmation est envoyée en cas de succès
    Étant donné une salle "S1" nommée "Alpha" d'une capacité de 10
    Et aucune réservation existante pour la salle "S1"
    Quand un utilisateur réserve la salle "S1" pour 4 participants du "2026-06-10 09:00" au "2026-06-10 10:00"
    Alors une confirmation est envoyée

  Scénario: Aucune confirmation n'est envoyée en cas d'échec
    Étant donné que la salle "S-INCONNUE" n'existe pas
    Quand un utilisateur réserve la salle "S-INCONNUE" pour 4 participants du "2026-06-10 09:00" au "2026-06-10 10:00"
    Alors aucune confirmation n'est envoyée
