// src/main/java/net/langlet/batch/processor/EnrichissementProcessor.java

package net.langlet.batch.processor;

import net.langlet.batch.model.AdresseSociete;
import net.langlet.batch.model.Societe;
import net.langlet.batch.model.SocieteComplete;
import net.langlet.batch.service.EnrichissementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EnrichissementProcessor implements ItemProcessor<Societe, SocieteComplete> {

    private static final Logger logger = LoggerFactory.getLogger(EnrichissementProcessor.class);

    private final EnrichissementService enrichissementService;

    public EnrichissementProcessor(EnrichissementService enrichissementService) {
        this.enrichissementService = enrichissementService;
    }

    @Override
    public SocieteComplete process(Societe societe) throws Exception {
        logger.debug("Traitement de la société avec le siret: {}", societe.getSiret());

        // Récupération de l'enrichissement depuis le cache (chargé par le listener)
        AdresseSociete enrichissement = enrichissementService.recupererEnrichissement(societe.getSiret());

        if (enrichissement != null) {
            logger.debug(
                "Enrichissement trouvé pour {}: adresse={}, codePostal={}, ville={}",
                societe.getSiret(),
                enrichissement.getAdresse(),
                enrichissement.getCodePostal(),
                enrichissement.getVille()
            );

            // Création de l'objet enrichi
            return SocieteComplete.fromInputData(societe, enrichissement);
        }
        else {
            return null;
        }
    }
}