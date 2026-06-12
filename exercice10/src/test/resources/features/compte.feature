# language: fr
Fonctionnalité: Création de compte
  En tant qu'utilisateur
  Je veux créer un compte
  Afin de pouvoir passer des commandes

  Scénario: Inscription réussie
    Étant donné que le compte "alice" n'existe pas
    Quand l'utilisateur s'inscrit avec l'email "alice@mail.com", le nom "alice" et le mot de passe "secret"
    Alors l'inscription est confirmée

  Scénario: Inscription avec un identifiant déjà existant
    Étant donné que le compte "alice" existe déjà
    Quand l'utilisateur s'inscrit avec l'email "alice@mail.com", le nom "alice" et le mot de passe "secret"
    Alors une erreur d'identifiant déjà utilisé est renvoyée
