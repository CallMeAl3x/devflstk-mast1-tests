# language: fr
Fonctionnalité: Suppression de produit d'une commande
  En tant qu'utilisateur
  Je veux supprimer des produits de ma commande

  Scénario: Diminuer la quantité d'un produit présent en plusieurs exemplaires
    Étant donné une commande "CMD-1" contenant 2 unités du produit "Clavier"
    Quand l'utilisateur supprime le produit "Clavier" de la commande "CMD-1"
    Alors la commande "CMD-1" contient 1 unité du produit "Clavier"

  Scénario: Supprimer un produit présent en un seul exemplaire le retire
    Étant donné une commande "CMD-1" contenant 1 unité du produit "Clavier"
    Quand l'utilisateur supprime le produit "Clavier" de la commande "CMD-1"
    Alors la commande "CMD-1" ne contient plus le produit "Clavier"

  Scénario: Supprimer un produit absent renvoie une erreur
    Étant donné une commande "CMD-1" vide
    Quand l'utilisateur supprime le produit "Clavier" de la commande "CMD-1"
    Alors une erreur de produit absent est renvoyée
