package net.elanglet.poc.batch.service;

import net.elanglet.poc.batch.api.ApiClient;
import net.elanglet.poc.batch.model.ApiData;
import net.elanglet.poc.batch.model.Item;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Step-scoped cache service that holds API data for the current chunk.
 */
@Component
@StepScope
public class ApiChunkCacheService {

    private final ApiClient apiClient;
    private Map<Long, String> enrichmentById = Collections.emptyMap();

    public ApiChunkCacheService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void prepareForChunk(List<Item> items) {
        if (items == null || items.isEmpty()) {
            enrichmentById = Collections.emptyMap();
            return;
        }
        List<ApiData> fetched = apiClient.fetchDataFor(items);
        enrichmentById = fetched.stream()
                .collect(Collectors.toMap(
                        ad -> {
                            String k = ad.key();
                            try {
                                return Long.parseLong(k.replaceFirst("^k-", ""));
                            } catch (Exception e) {
                                return -1L;
                            }
                        },
                        ApiData::value,
                        (a,b) -> a,
                        HashMap::new
                ));
    }

    public Optional<String> enrichmentFor(Item item) {
        if (enrichmentById == null || enrichmentById.isEmpty()) return Optional.empty();
        return Optional.ofNullable(enrichmentById.get(item.id()));
    }

    public void clear() {
        enrichmentById = Collections.emptyMap();
    }
}
