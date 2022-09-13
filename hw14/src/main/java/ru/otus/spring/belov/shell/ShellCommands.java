package ru.otus.spring.belov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.belov.service.JobService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final JobService jobService;

    @ShellMethod(value = "Запуск миграции", key = "run")
    @ShellMethodAvailability(value = "isAvailableToRun")
    public void startJob() throws Exception {
        jobService.run();
    }

    @ShellMethod(value = "Перезапуск", key = "restart")
    @ShellMethodAvailability(value = "isAvailableToRestart")
    public void restartJob() throws Exception {
        jobService.restart();
    }

    @ShellMethod(value = "Показать информацию о задаче", key = "info")
    @ShellMethodAvailability(value = "isAvailableToRerun")
    public String showJobInfo() throws Exception {
        return jobService.showJobInfo();
    }

    private Availability isAvailableToRun() {
        return jobService.isAlreadyRun() ?
                Availability.unavailable("Задача уже запущена. Попробуйте перезапустить.") :
                Availability.available();
    }

    private Availability isAvailableToRestart() {
        return jobService.isAlreadyRun() ? Availability.available() :
                Availability.unavailable("Задача ещё не запущена. Попробуйте запустить задачу.");
    }
}
