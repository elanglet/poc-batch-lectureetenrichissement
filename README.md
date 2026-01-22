# POC - Lecture et enrichissement de données avec Spring Batch

### Contexte

L'objectif de ce Proof Of Concepts est d'illustrer les bonnes pratiques de Spring Batch quant à la lecture de données et à leur enrichissement avant traitement.

### Le besoin et ses contraintes

Le besoin est le suivant : lire des données depuis une source et les enrichir avec d'autres données retournées par un appel d'API.

Pour des raisons évidente de performances, l'enrichissement se fait par lots, limitant ainsi le nombre d'appels d'API.

Il est nécessaire de respecter les responsabilités applicatives des composants Spring Batch, ainsi cet enrichissement doit se faire AVANT le traitement métier par le processor.

### Solution

Utiliser les différents écouteurs du cycle de vie (listeners) Spring Batch pour orienter l'exécution du flux en ce sens.

### Description du fonctionnement

#### 1 - Lecture : 
Le Reader lit 10 "Société" et le ReadListener accumule les numéros de Sirets.

#### 2 - Processing :

Le ProcessorEnrichissementListener détecte que le cache est vide et déclenche l'appel API.
L'API retourne les enrichissements qui sont mis en cache puis chaque item est enrichi via le cache.

#### 3 - Écriture : 

Les 10 "SocieteComplete" sont écrits.

#### 4 - Nettoyage : 

Le ChunkEnrichissementListener nettoie le cache.

### Exécution

Il s'agit d'un projet Spring Boot qui peut être lancé grace au plugin Maven pour Spring Boot : mvn spring-boot:run

Le fichier CSV d'entrée contient 53 sociétés fictives, la taille du chunk est de 10, le fichier de sortie doit contenir les 53 sociétés complètes en fin d'exécution.
Les traces dans la console permettent d'illustrer le séquencement des actions faites par les différents objets du batch.
