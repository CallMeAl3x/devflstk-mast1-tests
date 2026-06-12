# language: fr
Fonctionnalité: Gestion des tickets de support
  En tant qu'agent support
  Je veux gérer le cycle de vie des tickets
  Afin de suivre la résolution des incidents

  Scénario: Création d'un ticket valide
    Quand on crée un ticket "Connexion impossible" de priorité "LOW"
    Alors le ticket est créé avec le statut "OPEN"

  Scénario: Résolution d'un ticket ouvert
    Étant donné un ticket "Imprimante en panne" de priorité "HIGH"
    Quand on change le statut du ticket vers "RESOLVED"
    Alors le statut du ticket est "RESOLVED"

  Scénario: Un ticket résolu ne peut plus changer de statut
    Étant donné un ticket "Ecran cassé" de priorité "MEDIUM"
    Quand on change le statut du ticket vers "RESOLVED"
    Et on change le statut du ticket vers "IN_PROGRESS"
    Alors une erreur de transition interdite est renvoyée

  Scénario: Consultation d'un ticket inexistant
    Étant donné aucun ticket d'identifiant 9999
    Quand on consulte le ticket d'identifiant 9999
    Alors une erreur de ticket introuvable est renvoyée
