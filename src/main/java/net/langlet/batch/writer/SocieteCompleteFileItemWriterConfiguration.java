package net.langlet.batch.writer;

import net.langlet.batch.model.SocieteComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class SocieteCompleteFileItemWriterConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SocieteCompleteFileItemWriterConfiguration.class);

    private final String outputFile;

    public SocieteCompleteFileItemWriterConfiguration(
            @Value("${batch.output.file:entreprises-completes.csv}") String outputFile
    ) {
        this.outputFile = outputFile;
    }

    @Bean
    public FlatFileItemWriter<SocieteComplete> csvFileItemWriter() {

        logger.info("Configuration du FlatFileItemWriter pour le fichier: {}", outputFile);

        BeanWrapperFieldExtractor<SocieteComplete> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"siret", "nom", "adresse", "codePostal", "ville"});
        fieldExtractor.afterPropertiesSet();

        DelimitedLineAggregator<SocieteComplete> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<SocieteComplete>()
                .name("csvFileItemWriter")
                .resource(new FileSystemResource("target/" + outputFile))
                .lineAggregator(lineAggregator)
                .build();
    }
}
