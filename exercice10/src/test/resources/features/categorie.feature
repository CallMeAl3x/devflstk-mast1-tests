# language: fr
Fonctionnalité: Navigation par catégorie
  En tant qu'utilisateur
  Je veux naviguer par catégorie de produits
  Afin de découvrir ce qui est disponible

  Scénario: Sélection d'une catégorie
    Étant donné un catalogue contenant les produits suivants:
      | nom          | prix | categorie     |
      | Clavier méca | 80.0 | peripheriques |
      | Souris gamer | 40.0 | peripheriques |
      | Casque audio | 60.0 | audio         |
    Quand l'utilisateur sélectionne la catégorie "audio"
    Alors la recherche retourne 1 produit
    Et la recherche contient le produit "Casque audio"
