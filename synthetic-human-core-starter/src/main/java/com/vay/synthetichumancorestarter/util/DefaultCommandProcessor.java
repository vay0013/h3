package com.vay.synthetichumancorestarter.util;

import com.vay.synthetichumancorestarter.model.Command;
import com.vay.synthetichumancorestarter.model.Priority;
import com.vay.synthetichumancorestarter.service.MonitoringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@RequiredArgsConstructor
public class DefaultCommandProcessor implements CommandProcessor {
    private final CommandQueue commandQueue;
    private final AtomicBoolean busy = new AtomicBoolean(false);
    private final MonitoringService monitoringService;

    @Override
    public void submitCommand(@Valid Command command) {
        try {
            Runnable task = () -> executeCommand(command);
            if (command.getPriority().equals(Priority.CRITICAL)) {
                new Thread(task, "Critical command thread").start();
            } else {
                commandQueue.submit(task);
            }
        } catch (Exception e) {
            log.error("Exception in DefaultCommandProcessor", e);
            throw new RuntimeException(e);
        }
    }

    @WeylandWatchingYou
    private void executeCommand(Command command) {
        busy.set(true);
        log.info("Исполнение команды: {}", command);
        try {
            Thread.sleep(2000);
            log.info("Команда выполнена: {}", command);
            monitoringService.incrementAuthorCompleted(command.getAuthor(), command);
        } catch (Exception e) {
            log.error("Ошибка при исполнении команды: {}", command);
        } finally {
            busy.set(false);
        }
    }

    @Override
    public boolean isBusy() {
        return busy.get();
    }

    @Override
    public int getQueueSize() {
        return commandQueue.getQueueSize();
    }
}
