package com.example;

public interface UtilisateurRepository {

    boolean existeParNomUtilisateur(String nomUtilisateur);

    Utilisateur trouverParNomUtilisateur(String nomUtilisateur);

    void sauvegarder(Utilisateur utilisateur);
}
