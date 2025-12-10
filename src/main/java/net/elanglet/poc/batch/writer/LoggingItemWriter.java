package net.elanglet.poc.batch.writer;

import net.elanglet.poc.batch.model.EnrichedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoggingItemWriter implements ItemWriter<EnrichedItem> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingItemWriter.class);

    @Override
    public void write(Chunk<? extends EnrichedItem> items) throws Exception {
        items.forEach(
                i -> logger.info("Writing EnrichedItem: id={}, payload={}, enrichment={}",
                        i.id(), i.payload(), i.enrichment()));
    }
}
