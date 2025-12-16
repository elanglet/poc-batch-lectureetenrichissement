// src/main/java/net/langlet/batch/controller/FakeApiController.java

package net.langlet.batch.controller;

import net.langlet.batch.model.AdresseSociete;
import net.langlet.batch.model.ReponseAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller Mock pour simuler l'API d'enrichissement
 * À SUPPRIMER en production - c'est uniquement pour tester l'exemple
 */
@RestController
@RequestMapping("/api")
public class FakeApiController {

    private static final Logger logger = LoggerFactory.getLogger(FakeApiController.class);

    private static final List<AdresseSociete> listeDesAdresseSociete = List.of(
        // Correspondance avec les SIRET du fichier CSV
        new AdresseSociete("12345678901234", "15 rue de la Technologie", "75001", "Paris"),
        new AdresseSociete("23456789012345", "28 avenue du Soleil", "13001", "Marseille"),
        new AdresseSociete("34567890123456", "42 boulevard des Réparations", "69001", "Lyon"),
        new AdresseSociete("45678901234567", "7 place de la Santé", "31000", "Toulouse"),
        new AdresseSociete("56789012345678", "33 rue Gastronomique", "44000", "Nantes"),
        new AdresseSociete("67890123456789", "19 avenue de la Mode", "33000", "Bordeaux"),
        new AdresseSociete("78901234567890", "55 rue des Écrivains", "67000", "Strasbourg"),
        new AdresseSociete("89012345678901", "8 impasse des Artisans", "59000", "Lille"),
        new AdresseSociete("90123456789012", "12 boulevard Moderne", "35000", "Rennes"),
        new AdresseSociete("01234567890123", "24 rue de la Nature", "34000", "Montpellier"),

        new AdresseSociete("11234567890124", "46 place des Joyaux", "06000", "Nice"),
        new AdresseSociete("21234567890125", "3 avenue de la Conduite", "49000", "Angers"),
        new AdresseSociete("31234567890126", "88 boulevard de l'Habitat", "76000", "Rouen"),
        new AdresseSociete("41234567890127", "17 rue de l'Image", "21000", "Dijon"),
        new AdresseSociete("51234567890128", "5 impasse du Bois", "25000", "Besançon"),
        new AdresseSociete("61234567890129", "31 avenue des Fleurs", "51100", "Reims"),
        new AdresseSociete("71234567890130", "22 rue Napolitaine", "38000", "Grenoble"),
        new AdresseSociete("81234567890131", "9 place du Pressing", "87000", "Limoges"),
        new AdresseSociete("91234567890132", "14 rue Tradition", "86000", "Poitiers"),
        new AdresseSociete("02234567890133", "37 avenue des Terroirs", "45000", "Orléans"),

        new AdresseSociete("12234567890134", "50 boulevard Vétérinaire", "37000", "Tours"),
        new AdresseSociete("22234567890135", "11 place Centrale", "63000", "Clermont-Ferrand"),
        new AdresseSociete("32234567890136", "26 rue de la Vision", "68100", "Mulhouse"),
        new AdresseSociete("42234567890137", "18 avenue Harmonie", "80000", "Amiens"),
        new AdresseSociete("52234567890138", "43 boulevard Outillage", "54000", "Nancy"),
        new AdresseSociete("62234567890139", "29 rue Crescendo", "42000", "Saint-Étienne"),
        new AdresseSociete("72234567890140", "6 place du Sourire", "29200", "Brest"),
        new AdresseSociete("82234567890141", "39 avenue Action", "57000", "Metz"),
        new AdresseSociete("92234567890142", "16 rue Affinage", "76100", "Le Havre"),
        new AdresseSociete("03234567890143", "21 boulevard Express", "83000", "Toulon"),

        new AdresseSociete("13234567890144", "35 rue des Anges", "14000", "Caen"),
        new AdresseSociete("23234567890145", "48 avenue Sérénité", "30000", "Nîmes"),
        new AdresseSociete("33234567890146", "13 place Délice", "44100", "Nantes"),
        new AdresseSociete("43234567890147", "27 rue des Amis", "69002", "Lyon"),
        new AdresseSociete("53234567890148", "52 boulevard Évasion", "75008", "Paris"),
        new AdresseSociete("63234567890149", "10 avenue Speed", "13008", "Marseille"),
        new AdresseSociete("73234567890150", "41 rue Formation", "31200", "Toulouse"),
        new AdresseSociete("83234567890151", "23 impasse Gourmande", "33200", "Bordeaux"),
        new AdresseSociete("93234567890152", "36 quai Marée", "44200", "Nantes"),
        new AdresseSociete("04234567890153", "20 rue Compagnons", "59200", "Lille"),

        new AdresseSociete("14234567890154", "44 boulevard FitZone", "67200", "Strasbourg"),
        new AdresseSociete("24234567890155", "4 place Art & Encre", "35200", "Rennes"),
        new AdresseSociete("34234567890156", "47 avenue Aluminium", "34200", "Montpellier"),
        new AdresseSociete("44234567890157", "25 rue Saveurs", "06200", "Nice"),
        new AdresseSociete("54234567890158", "32 boulevard Expertise", "49100", "Angers"),
        new AdresseSociete("64234567890159", "38 place TechStore", "76100", "Rouen"),
        new AdresseSociete("74234567890160", "1 avenue Éternité", "21100", "Dijon"),
        new AdresseSociete("84234567890161", "49 rue Jardins", "25100", "Besançon"),
        new AdresseSociete("94234567890162", "30 boulevard AutoLib", "51000", "Reims"),
        new AdresseSociete("05234567890163", "2 impasse Zen", "38100", "Grenoble"),

        new AdresseSociete("15234567890164", "34 rue Couture", "87100", "Limoges"),
        new AdresseSociete("25234567890165", "45 avenue Digital", "86100", "Poitiers"),
        new AdresseSociete("35234567890166", "40 boulevard Analyses", "45100", "Orléans")
    );

    @PostMapping("/adresses")
    public ReponseAPI adressesEndpoint(@RequestBody List<String> sirets) {
        logger.info("--------------------------------------");
        logger.info("- Appel API                          - ");
        logger.info("- Nombre de siret reçues: {}         - ", sirets.size());
        logger.info("--------------------------------------");

        // Simuler un léger délai d'API
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Générer des données d'enrichissement pour chaque siret
        List<AdresseSociete> listeAdressesSocietes = sirets.stream()
                .map(this::genererEnrichissement)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        ReponseAPI response = new ReponseAPI(
                listeAdressesSocietes,
                listeAdressesSocietes.size(),
                "SUCCESS"
        );

        logger.info("Réponse API. Contient {} adresses", listeAdressesSocietes.size());

        return response;
    }

    private Optional<AdresseSociete> genererEnrichissement(String siret) {
        return FakeApiController.listeDesAdresseSociete.stream().filter(donnee -> donnee.getSiret().equals(siret)).findFirst();
    }
}