# Spring Batch API Enrichment - Full Project

Package: net.elanglet.poc.batch

This project demonstrates:
- A chunk-prefetching reader that reads a full chunk and asks ApiChunkCacheService to prepare data
- ApiChunkCacheService calls ApiClient to fetch API data for the whole chunk and serves enrichment per item
- ApiEnrichmentProcessor uses the cache service to enrich items
- BusinessProcessor performs domain logic
- LoggingItemWriter logs the enriched items

Run:
  mvn -DskipTests spring-boot:run
