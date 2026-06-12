# language: fr
Fonctionnalité: Ajout de produit à une commande
  En tant qu'utilisateur
  Je veux ajouter des produits à ma commande

  Scénario: Ajout d'un nouveau produit
    Étant donné une commande "CMD-1" vide
    Quand l'utilisateur ajoute le produit "Clavier" à la commande "CMD-1"
    Alors l'ajout est confirmé
    Et la commande "CMD-1" contient 1 unité du produit "Clavier"

  Scénario: Ajout d'un produit déjà présent augmente la quantité
    Étant donné une commande "CMD-1" contenant 1 unité du produit "Clavier"
    Quand l'utilisateur ajoute le produit "Clavier" à la commande "CMD-1"
    Alors la commande "CMD-1" contient 2 unités du produit "Clavier"

  Scénario: Ajout à une commande inexistante
    Étant donné que la commande "CMD-404" n'existe pas
    Quand l'utilisateur ajoute le produit "Clavier" à la commande "CMD-404"
    Alors une erreur de commande introuvable est renvoyée
