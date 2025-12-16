// src/main/java/net/langlet/batch/listener/ProcessorEnrichissementListener.java

package net.langlet.batch.listener;

import net.langlet.batch.model.Societe;
import net.langlet.batch.model.SocieteComplete;
import net.langlet.batch.service.EnrichissementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
public class ProcessorEnrichissementListener implements ItemProcessListener<Societe, SocieteComplete> {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorEnrichissementListener.class);

    private final EnrichissementService enrichissementService;

    public ProcessorEnrichissementListener(EnrichissementService enrichissementService) {
        this.enrichissementService = enrichissementService;
    }

    public void beforeProcess(Societe societe) {
        if(enrichissementService.recupererTailleDuCache() == 0)
            enrichissementService.prechargerEnrichissements();
    }

}
