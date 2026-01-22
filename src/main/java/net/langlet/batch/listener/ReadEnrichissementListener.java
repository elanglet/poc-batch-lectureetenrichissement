// src/main/java/net/langlet/batch/listener/ReadEnrichissementListener.java

package net.langlet.batch.listener;

import net.langlet.batch.model.Societe;
import net.langlet.batch.service.EnrichissementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

/**
 * Listener qui précharge les enrichissements au niveau du chunk
 */
@Component
public class ReadEnrichissementListener implements ItemReadListener<Societe> {

    private static final Logger logger = LoggerFactory.getLogger(ReadEnrichissementListener.class);

    private final EnrichissementService enrichissementService;

    public ReadEnrichissementListener(EnrichissementService enrichissementService) {
        this.enrichissementService = enrichissementService;
    }

    @Override
    public void afterRead(Societe societe) {
        // Accumuler les sirets lues
        enrichissementService.getSiretsLus().get().add(societe.getSiret());
        enrichissementService.getNombreDeSirets().set(enrichissementService.getNombreDeSirets().get() + 1);

        logger.debug("Société lue: {} (total dans le chunk: {})", societe.getSiret(), enrichissementService.getNombreDeSirets().get());
    }

    @Override
    public void onReadError(Exception e) {
        logger.error("Erreur lors de la lecture, réinitialisation du cache de sirets", e);
        enrichissementService.getSiretsLus().get().clear();
        enrichissementService.getNombreDeSirets().set(0);
    }
}