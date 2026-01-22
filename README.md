# POC - Lecture et enrichissement de données avec Spring Batch

### Contexte

L'objectif de ce Proof Of Concepts est d'illustrer les bonnes pratiques de Spring Batch quant à la lecture de données et à leur enrichissement avant traitement.

### Le besoin et ses contraintes

Le besoin est le suivant : lire des données depuis une source et les enrichir avec d'autres données retournées par un appel d'API.

Pour des raisons évidente de performances, l'enrichissement se fait par lots, limitant ainsi le nombre d'appels d'API.

Il est nécessaire de respecter les responsabilités applicatives des composants Spring Batch, ainsi cet enrichissement doit se faire AVANT le traitement métier par le processor.

### Solution

Utiliser les différents écouteurs du cycle de vie (listeners) Spring Batch pour orienter l'exécution du flux en ce sens.