package com.example;

public class ServiceCommande {

    private final CommandeRepository commandes;

    public ServiceCommande(CommandeRepository commandes) {
        this.commandes = commandes;
    }

    /** US5 — ajout d'un produit à une commande. */
    public String ajouterProduit(String commandeId, Produit produit) {
        Commande commande = commandeExistante(commandeId);
        commande.ajouter(produit);
        commandes.sauvegarder(commande);
        return "Produit " + produit.nom() + " ajouté à la commande " + commandeId;
    }

    /** US6 — suppression / diminution d'un produit d'une commande. */
    public void retirerProduit(String commandeId, String produitId) {
        Commande commande = commandeExistante(commandeId);
        commande.retirer(produitId);
        commandes.sauvegarder(commande);
    }

    /** US7 — validation d'une commande. */
    public String valider(String commandeId) {
        Commande commande = commandeExistante(commandeId);
        return "Commande " + commande.getId() + " validée";
    }

    private Commande commandeExistante(String commandeId) {
        Commande commande = commandes.trouver(commandeId);
        if (commande == null) {
            throw new CommandeIntrouvableException(commandeId);
        }
        return commande;
    }
}
