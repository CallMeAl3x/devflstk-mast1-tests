# language: fr
Fonctionnalité: Recherche de produits
  En tant qu'utilisateur
  Je veux rechercher des produits
  Afin de trouver rapidement ce dont j'ai besoin

  Contexte:
    Étant donné un catalogue contenant les produits suivants:
      | nom          | prix | categorie     |
      | Clavier méca | 80.0 | peripheriques |
      | Souris gamer | 40.0 | peripheriques |
      | Casque audio | 60.0 | audio         |

  Scénario: Recherche par mot-clé
    Quand l'utilisateur recherche le mot-clé "clavier"
    Alors la recherche retourne 1 produit
    Et la recherche contient le produit "Clavier méca"

  Scénario: Recherche par prix maximum
    Quand l'utilisateur recherche les produits à 50 € maximum
    Alors la recherche retourne 1 produit
    Et la recherche contient le produit "Souris gamer"
