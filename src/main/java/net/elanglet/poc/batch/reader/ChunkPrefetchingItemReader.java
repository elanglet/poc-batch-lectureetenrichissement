package net.elanglet.poc.batch.reader;

import net.elanglet.poc.batch.model.Item;
import net.elanglet.poc.batch.service.ApiChunkCacheService;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@StepScope
public class ChunkPrefetchingItemReader implements ItemReader<Item> {

    private final ItemReader<Item> delegate;
    private final int chunkSize;
    private final ApiChunkCacheService cacheService;

    private Iterator<Item> currentBatchIterator = null;

    public ChunkPrefetchingItemReader(ItemReader<Item> delegate, int chunkSize, ApiChunkCacheService cacheService) {
        this.delegate = delegate;
        this.chunkSize = chunkSize;
        this.cacheService = cacheService;
    }

    @Override
    public Item read() throws Exception {
        if (currentBatchIterator == null || !currentBatchIterator.hasNext()) {
            List<Item> buffer = new ArrayList<>(chunkSize);
            for (int i = 0; i < chunkSize; i++) {
                Item it = delegate.read();
                if (it == null) break;
                buffer.add(it);
            }
            if (buffer.isEmpty()) {
                return null;
            }
            // Prepare cache for the whole chunk (this calls the API through cacheService)
            cacheService.prepareForChunk(buffer);
            currentBatchIterator = buffer.iterator();
        }
        return currentBatchIterator.hasNext() ? currentBatchIterator.next() : null;
    }
}
