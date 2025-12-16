package net.langlet.batch.config;

import net.langlet.batch.listener.ChunkEnrichissementListener;
import net.langlet.batch.listener.ProcessorEnrichissementListener;
import net.langlet.batch.listener.ReadEnrichissementListener;
import net.langlet.batch.model.Societe;
import net.langlet.batch.model.SocieteComplete;
import net.langlet.batch.processor.EnrichissementProcessor;
import net.langlet.batch.processor.MetierProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class BatchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    private final int chunkSize;
    private final FlatFileItemReader<Societe> reader;
    private final EnrichissementProcessor enrichissementProcessor;
    private final MetierProcessor metierProcessor;
    private final FlatFileItemWriter<SocieteComplete> writer;

    private final ReadEnrichissementListener readEnrichissementListener;
    private final ProcessorEnrichissementListener processorEnrichissementListener;
    private final ChunkEnrichissementListener chunkEnrichissementListener;

    public BatchConfiguration(
            @Value("${batch.chunk.size:10}") int chunkSize,
            FlatFileItemReader<Societe> reader,
            EnrichissementProcessor processor,
            MetierProcessor metierProcessor,
            FlatFileItemWriter<SocieteComplete> writer,
            ReadEnrichissementListener readEnrichissementListener,
            ProcessorEnrichissementListener processorEnrichissementListener,
            ChunkEnrichissementListener chunkEnrichissementListener
    ) {
        this.chunkSize = chunkSize;
        this.reader = reader;
        this.enrichissementProcessor = processor;
        this.metierProcessor = metierProcessor;
        this.writer = writer;
        this.readEnrichissementListener = readEnrichissementListener;
        this.processorEnrichissementListener = processorEnrichissementListener;
        this.chunkEnrichissementListener = chunkEnrichissementListener;
    }

    @Bean
    public Step lectureEtEnrichissementStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ) {

        logger.info("Configuration du step avec chunk size: {}", chunkSize);

        return new StepBuilder("lectureEtEnrichissementStep", jobRepository)
                .<Societe, SocieteComplete>chunk(chunkSize, transactionManager)
                .reader(reader)
                .processor(enrichissementProcessor)
                .processor(compositeProcessor())
                .writer(writer)
                .listener(readEnrichissementListener)
                .listener(processorEnrichissementListener)
                .listener(chunkEnrichissementListener)
                .build();
    }

    @Bean
    public Job lectureEtEnrichissementJob(
            JobRepository jobRepository,
            Step lectureEtEnrichissementStep
    ) {
        return new JobBuilder("lectureEtEnrichissementJob", jobRepository)
                .start(lectureEtEnrichissementStep)
                .build();
    }

    @Bean
    public CompositeItemProcessor<Societe, SocieteComplete> compositeProcessor() {
        List<ItemProcessor<?, ?>> delegates = new ArrayList<>(2);
        delegates.add(enrichissementProcessor);
        delegates.add(metierProcessor);

        CompositeItemProcessor<Societe, SocieteComplete> processor = new CompositeItemProcessor<>();

        processor.setDelegates(delegates);

        return processor;
    }
}