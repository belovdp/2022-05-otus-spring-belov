package ru.otus.spring.belov.config.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.belov.domain.document.AuthorDocument;
import ru.otus.spring.belov.domain.relation.Author;
import ru.otus.spring.belov.mappers.EntityMapper;

import javax.persistence.EntityManagerFactory;

/**
 * Шаг миграции авторов
 */
@RequiredArgsConstructor
@Configuration
public class AuthorMigrationStepConfig {
    private final EntityManagerFactory managerFactory;
    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityMapper mapper;

    @StepScope
    @Bean
    public JpaPagingItemReader<Author> authorReader() {
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorReader")
                .entityManagerFactory(managerFactory)
                .queryString("select a from Author a")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, AuthorDocument> authorProcessor() {
        return mapper::toAuthorDocument;
    }

    @StepScope
    @Bean
    public MongoItemWriter<AuthorDocument> authorWriter() {
        return new MongoItemWriterBuilder<AuthorDocument>()
                .collection("authors")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step authorMigrationStep(JpaPagingItemReader<Author> authorReader,
                                    ItemProcessor<Author, AuthorDocument> authorProcessor,
                                    MongoItemWriter<AuthorDocument> authorWriter) {
        return stepBuilderFactory.get("authorMigrationStep")
                .<Author, AuthorDocument>chunk(3)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .allowStartIfComplete(Boolean.TRUE)
                .build();
    }
}
