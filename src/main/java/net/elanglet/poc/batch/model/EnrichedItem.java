package net.elanglet.poc.batch.model;

public record EnrichedItem(long id, String payload, String enrichment) {
    public static EnrichedItem from(Item item, String enrichment) {
        return new EnrichedItem(item.id(), item.payload(), enrichment);
    }
}
