package ru.otus.spring.belov.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.belov.domain.document.AuthorDocument;
import ru.otus.spring.belov.domain.document.BookCommentDocument;
import ru.otus.spring.belov.domain.document.BookDocument;
import ru.otus.spring.belov.domain.document.GenreDocument;

import static java.lang.String.format;

/**
 * Конфигурация задачи
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfig {

    /** Фабрика для формирования задачи */
    private final JobBuilderFactory jobBuilderFactory;
    /** Фабрика формирования шагов задачи */
    private final StepBuilderFactory stepBuilderFactory;
    /** Работа с Mongo */
    private final MongoTemplate mongoTemplate;

    /**
     * Возвращает шаг удаления БД mongo
     * @return шаг удаления БД mongo
     */
    @Bean
    public TaskletStep dropMongoDBStep() {
        return stepBuilderFactory
                .get("dropMongoDBStep")
                .tasklet((stepContribution, chunkContext) -> {
                    mongoTemplate.getDb().drop();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    /**
     * Возвращает шаг удаления вспомогательных данных (поля со старыми идентификаторами реляционной БД)
     * @return шаг удаления вспомогательных данных
     */
    @Bean
    public TaskletStep dropOldIdsStep() {
        return stepBuilderFactory
                .get("dropOldIdsStep")
                .tasklet((stepContribution, chunkContext) -> {
                    var query = new Query();
                    var update = new Update().unset("oldId");
                    mongoTemplate.findAndModify(query, update, GenreDocument.class);
                    mongoTemplate.findAndModify(query, update, AuthorDocument.class);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    /**
     * Возвращает задачу миграции
     * @param authorMigrationStep шиг миграции авторов
     * @param genreMigrationStep  шаг миграции жанров
     * @param bookMigrationStep   шаг миграции книг
     * @return задача миграции
     */
    @Bean
    public Job migrateToMongoJob(Step authorMigrationStep,
                                    Step genreMigrationStep,
                                    Step bookMigrationStep) {
        return jobBuilderFactory.get("migrateToMongoJob")
                .incrementer(new RunIdIncrementer())
                .start(dropMongoDBStep())
                .next(authorMigrationStep)
                .next(genreMigrationStep)
                .next(bookMigrationStep)
                .next(dropOldIdsStep())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Начало миграции.");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("Миграция завершена.");
                    }
                })
                .build();
    }
}
