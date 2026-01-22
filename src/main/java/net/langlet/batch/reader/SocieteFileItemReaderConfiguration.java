// src/main/java/net/langlet/batch/reader/SocieteFileItemReaderConfiguration.java

package net.langlet.batch.reader;

import net.langlet.batch.model.Societe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;

/**
 * Configuration du FlatFileItemReader pour lire le fichier CSV des entreprises
 */
@Configuration
public class SocieteFileItemReaderConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SocieteFileItemReaderConfiguration.class);

    private final String inputFile;
    private final String fileLocation;

    public SocieteFileItemReaderConfiguration(
            @Value("${batch.input.file:entreprises.csv}")String inputFile,
            @Value("${batch.file.location:classpath}")String fileLocation
    ) {
        this.inputFile = inputFile;
        this.fileLocation = fileLocation;
    }

    @Bean
    public FlatFileItemReader<Societe> csvFileItemReader() {
        logger.info("Configuration du FlatFileItemReader pour le fichier: {}", inputFile);

        FlatFileItemReaderBuilder<Societe> builder = new FlatFileItemReaderBuilder<Societe>()
                .name("csvFileItemReader")
                .delimited()
                .delimiter(";")
                .names("siret", "nom")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Societe.class);
                }})
                .strict(true); // Échoue si le fichier n'existe pas

        if ("classpath".equalsIgnoreCase(fileLocation)) {
            logger.info("Lecture depuis classpath: {}", inputFile);
            builder.resource(new ClassPathResource(inputFile));
        } else {
            logger.info("Lecture depuis filesystem: {}", inputFile);
            builder.resource(new FileSystemResource(new File(inputFile)));
        }

        FlatFileItemReader<Societe> reader = builder.build();
        reader.setEncoding("UTF-8");

        return reader;
    }
}