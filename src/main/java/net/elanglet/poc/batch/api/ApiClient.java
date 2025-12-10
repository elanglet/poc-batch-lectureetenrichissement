package net.elanglet.poc.batch.api;

import net.elanglet.poc.batch.model.ApiData;
import net.elanglet.poc.batch.model.Item;

import java.util.List;

public interface ApiClient {
    List<ApiData> fetchDataFor(List<Item> items);
}
