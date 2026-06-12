package com.example;

public class ServiceCompte {

    private final UtilisateurRepository utilisateurs;

    public ServiceCompte(UtilisateurRepository utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    /** US1 — inscription. */
    public String inscrire(String email, String nomUtilisateur, String motDePasse) {
        if (utilisateurs.existeParNomUtilisateur(nomUtilisateur)) {
            throw new IdentifiantDejaUtiliseException(nomUtilisateur);
        }
        utilisateurs.sauvegarder(new Utilisateur(email, nomUtilisateur, motDePasse));
        return "Compte créé pour " + nomUtilisateur;
    }

    /** US2 — connexion. Renvoie la page d'accueil en cas de succès. */
    public String connecter(String nomUtilisateur, String motDePasse) {
        Utilisateur utilisateur = utilisateurs.trouverParNomUtilisateur(nomUtilisateur);
        if (utilisateur == null || !utilisateur.motDePasse().equals(motDePasse)) {
            throw new ConnexionEchoueeException();
        }
        return "accueil";
    }
}
