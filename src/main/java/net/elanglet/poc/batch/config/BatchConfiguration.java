package net.elanglet.poc.batch.config;

import net.elanglet.poc.batch.model.EnrichedItem;
import net.elanglet.poc.batch.model.Item;
import net.elanglet.poc.batch.processor.ApiEnrichmentProcessor;
import net.elanglet.poc.batch.processor.BusinessProcessor;
import net.elanglet.poc.batch.reader.ChunkPrefetchingItemReader;
import net.elanglet.poc.batch.reader.SimpleItemReader;
import net.elanglet.poc.batch.service.ApiChunkCacheService;
import net.elanglet.poc.batch.writer.LoggingItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
public class BatchConfiguration {

    @Bean
    public Job job(JobRepository jobRepository, @Qualifier("step1") Step step1) {
        return new JobBuilder("job-enrichissement", jobRepository)
                .preventRestart()
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                       SimpleItemReader simpleReader,
                       ApiEnrichmentProcessor enrichmentProcessor,
                       BusinessProcessor businessProcessor,
                       LoggingItemWriter writer,
                       ApiChunkCacheService cacheService,
                       @Value("${app.chunk.size:5}") int chunkSize) {

        CompositeItemProcessor<Item, EnrichedItem> compositeProcessor = new CompositeItemProcessor<>();
        compositeProcessor.setDelegates(Arrays.asList(enrichmentProcessor, businessProcessor));

        ChunkPrefetchingItemReader reader = new ChunkPrefetchingItemReader(simpleReader, chunkSize, cacheService);

        return new StepBuilder("step1", jobRepository)
                .<Item, EnrichedItem> chunk(5, transactionManager)
                .reader(reader)
                .processor(compositeProcessor)
                .writer(writer).build();
    }
}
