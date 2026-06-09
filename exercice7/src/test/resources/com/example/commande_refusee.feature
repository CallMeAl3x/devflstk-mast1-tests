# language: fr
Fonctionnalité: Commande refusée
  En tant que boutique en ligne
  Je veux refuser une commande invalide
  Afin de protéger le stock et la cohérence du catalogue

  Scénario: La commande est refusée si le produit est inconnu
    Étant donné que le produit "REF-INCONNU" n'existe pas dans le catalogue
    Et un client de profil "STANDARD"
    Quand le client commande 1 unités du produit "REF-INCONNU"
    Alors la commande est refusée car le produit est inconnu

  Scénario: La commande est refusée si le stock est insuffisant
    Étant donné un produit "REF-1" nommé "Clavier" au prix unitaire de 100.0 avec un stock de 3
    Et un client de profil "STANDARD"
    Quand le client commande 5 unités du produit "REF-1"
    Alors la commande est refusée car le stock est insuffisant
