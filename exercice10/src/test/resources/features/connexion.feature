# language: fr
Fonctionnalité: Connexion
  En tant qu'utilisateur
  Je veux me connecter à mon compte
  Afin d'accéder à l'application et passer des commandes

  Scénario: Connexion réussie
    Étant donné que le compte "alice" existe avec le mot de passe "secret"
    Quand l'utilisateur se connecte avec le nom "alice" et le mot de passe "secret"
    Alors l'utilisateur est redirigé vers la page d'accueil

  Scénario: Connexion échouée
    Étant donné que le compte "alice" existe avec le mot de passe "secret"
    Quand l'utilisateur se connecte avec le nom "alice" et le mot de passe "faux"
    Alors un message d'erreur de connexion est affiché
