package net.elanglet.poc.batch.processor;

import net.elanglet.poc.batch.model.EnrichedItem;
import net.elanglet.poc.batch.model.Item;
import net.elanglet.poc.batch.reader.SimpleItemReader;
import net.elanglet.poc.batch.service.ApiChunkCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@StepScope
@Component
public class ApiEnrichmentProcessor implements ItemProcessor<Item, EnrichedItem> {

    private static final Logger logger = LoggerFactory.getLogger(ApiEnrichmentProcessor.class);

    private final ApiChunkCacheService cacheService;

    public ApiEnrichmentProcessor(ApiChunkCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public EnrichedItem process(Item item) throws Exception {
        logger.info("Enrich Item: {}", item.id());
        Optional<String> enrich = cacheService.enrichmentFor(item);
        return EnrichedItem.from(item, enrich.orElse("NO_ENRICHMENT"));
    }
}
