package com.example;

public class ServiceCommande {

    private final CatalogueProduit catalogue;

    public ServiceCommande(CatalogueProduit catalogue) {
        this.catalogue = catalogue;
    }

    public RecuCommande passerCommande(Commande commande, ProfilClient profil) {
        Produit produit = catalogue.trouverParReference(commande.referenceProduit());
        if (produit == null) {
            throw new ProduitInconnuException(commande.referenceProduit());
        }
        if (commande.quantite() > produit.stockDisponible()) {
            throw new StockInsuffisantException(
                    commande.referenceProduit(), commande.quantite(), produit.stockDisponible());
        }

        double montantBrut = produit.prixUnitaire() * commande.quantite();
        double montantTotal = montantBrut * (1 - profil.tauxRemise());

        return new RecuCommande(
                produit.reference(),
                commande.quantite(),
                montantTotal,
                "Commande confirmée pour " + commande.quantite() + " x " + produit.nom());
    }
}
