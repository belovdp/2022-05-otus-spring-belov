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
import ru.otus.spring.belov.domain.document.BookDocument;
import ru.otus.spring.belov.domain.relation.Book;
import ru.otus.spring.belov.mappers.EntityMapper;

import javax.persistence.EntityManagerFactory;

/**
 * Шаг миграции книг
 */
@Configuration
@RequiredArgsConstructor
public class BookMigrationStepConfig {
    private final EntityManagerFactory managerFactory;
    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityMapper mapper;

    @StepScope
    @Bean
    public JpaPagingItemReader<Book> bookReader() {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookReader")
                .entityManagerFactory(managerFactory)
                .queryString("select b from Book b")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookDocument> bookProcessor() {
        return mapper::toBookDocument;
    }

    @StepScope
    @Bean
    public MongoItemWriter<BookDocument> bookWriter() {
        return new MongoItemWriterBuilder<BookDocument>()
                .collection("books")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step bookMigrationStep(JpaPagingItemReader<Book> bookReader,
                                  ItemProcessor<Book, BookDocument> bookProcessor,
                                  MongoItemWriter<BookDocument> bookWriter) {
        return stepBuilderFactory.get("bookMigrationStep")
                .<Book, BookDocument>chunk(3)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .allowStartIfComplete(Boolean.TRUE)
                .build();
    }
}
