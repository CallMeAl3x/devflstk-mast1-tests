# language: fr
Fonctionnalité: Commande acceptée
  En tant que boutique en ligne
  Je veux accepter une commande valide
  Afin de générer un reçu avec la remise correspondant au profil client

  Plan du Scénario: Une commande valide est acceptée et la remise du profil est appliquée
    Étant donné un produit "REF-1" nommé "Clavier" au prix unitaire de 100.0 avec un stock de 10
    Et un client de profil "<profil>"
    Quand le client commande 2 unités du produit "REF-1"
    Alors la commande est acceptée
    Et le reçu indique une quantité de 2
    Et le reçu indique un montant total de <montant>
    Et le reçu contient un message de confirmation

    Exemples:
      | profil   | montant |
      | STANDARD | 200.0   |
      | PREMIUM  | 180.0   |
      | VIP      | 160.0   |
