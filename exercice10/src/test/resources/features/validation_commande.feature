# language: fr
Fonctionnalité: Validation de commande
  En tant qu'utilisateur
  Je veux valider une commande

  Scénario: Validation d'une commande existante
    Étant donné une commande "CMD-1" contenant 1 unité du produit "Clavier"
    Quand l'utilisateur valide la commande "CMD-1"
    Alors la commande est confirmée

  Scénario: Validation d'une commande inexistante
    Étant donné que la commande "CMD-404" n'existe pas
    Quand l'utilisateur valide la commande "CMD-404"
    Alors une erreur de commande introuvable est renvoyée
