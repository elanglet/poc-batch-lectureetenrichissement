package net.langlet.batch.listener;

import net.langlet.batch.service.EnrichissementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ChunkEnrichissementListener implements ChunkListener {

    private static final Logger logger = LoggerFactory.getLogger(ChunkEnrichissementListener.class);

    private final EnrichissementService enrichissementService;

    public ChunkEnrichissementListener(EnrichissementService enrichissementService) {
        this.enrichissementService = enrichissementService;
    }

    @Override
    public void beforeChunk(ChunkContext context) {
        logger.debug("Début du chunk");
    }

    @Override
    public void afterChunk(ChunkContext context) {
        // Nettoyer le cache après traitement du chunk
        int cacheSize = enrichissementService.recupererTailleDuCache();
        enrichissementService.viderCache();
        logger.debug("Fin du chunk - Cache nettoyé (contenait {} entrées)", cacheSize);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        logger.error("Erreur durant le traitement du chunk, nettoyage du cache");
        enrichissementService.viderCache();
    }
}