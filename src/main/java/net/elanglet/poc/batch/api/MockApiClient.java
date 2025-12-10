package net.elanglet.poc.batch.api;

import net.elanglet.poc.batch.model.ApiData;
import net.elanglet.poc.batch.model.Item;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MockApiClient implements ApiClient {

    @Override
    public List<ApiData> fetchDataFor(List<Item> items) {
        try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        List<ApiData> out = new ArrayList<>(items.size());
        for (Item it : items) {
            out.add(new ApiData("k-" + it.id(), "value-for-" + it.id()));
        }
        return out;
    }
}
