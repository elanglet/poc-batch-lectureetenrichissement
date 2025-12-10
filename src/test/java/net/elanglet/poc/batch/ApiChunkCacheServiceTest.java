package net.elanglet.poc.batch;

import net.elanglet.poc.batch.api.ApiClient;
import net.elanglet.poc.batch.model.ApiData;
import net.elanglet.poc.batch.model.Item;
import net.elanglet.poc.batch.service.ApiChunkCacheService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiChunkCacheServiceTest {

    @Test
    void testPrepareAndEnrichment() {
        ApiClient mockApi = Mockito.mock(ApiClient.class);
        List<Item> items = List.of(new Item(1,"p1"), new Item(2,"p2"));
        Mockito.when(mockApi.fetchDataFor(items)).thenReturn(List.of(
                new ApiData("k-1","v1"),
                new ApiData("k-2","v2")
        ));

        ApiChunkCacheService svc = new ApiChunkCacheService(mockApi);
        svc.prepareForChunk(items);

        Optional<String> e1 = svc.enrichmentFor(new Item(1,"p1"));
        Optional<String> e2 = svc.enrichmentFor(new Item(2,"p2"));

        assertThat(e1).hasValue("v1");
        assertThat(e2).hasValue("v2");
    }
}
