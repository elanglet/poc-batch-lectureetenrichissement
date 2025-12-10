package net.elanglet.poc.batch.processor;

import net.elanglet.poc.batch.model.EnrichedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BusinessProcessor implements ItemProcessor<EnrichedItem, EnrichedItem> {

    private static final Logger logger = LoggerFactory.getLogger(BusinessProcessor.class);

    @Override
    public EnrichedItem process(EnrichedItem item) {
        logger.debug("Business processing item id={} enrichment={}", item.id(), item.enrichment());
        String newEnrichment = item.enrichment() + "-processed";
        return new EnrichedItem(item.id(), item.payload(), newEnrichment);
    }
}
