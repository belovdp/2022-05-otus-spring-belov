package ru.otus.spring.belov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Service;

/**
 * Сервис по работе с задачей
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    /** Операции над задачей */
    private final JobOperator jobOperator;
    /** Идентификатор запущенной задачи */
    private Long executionId;

    @Override
    public void run() throws Exception {
        if (executionId != null) {
            throw new IllegalArgumentException("Задача уже запущена");
        }
        executionId = jobOperator.start("migrateToMongoJob", "");
        log.info("Задача \"{}\" выполнена", jobOperator.getSummary(executionId));
    }

    @Override
    public void restart() throws Exception {
        if (executionId == null) {
            throw new IllegalArgumentException("Задача ещё не запущена");
        }
        jobOperator.restart(executionId);
    }

    @Override
    public String showJobInfo() throws Exception {
        return jobOperator.getSummary(executionId);
    }

    @Override
    public boolean isAlreadyRun() {
        return executionId != null;
    }
}
