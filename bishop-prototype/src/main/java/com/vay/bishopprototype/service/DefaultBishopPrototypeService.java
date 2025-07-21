package com.vay.bishopprototype.service;

import com.vay.synthetichumancorestarter.model.Command;
import com.vay.synthetichumancorestarter.service.MonitoringService;
import com.vay.synthetichumancorestarter.util.CommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultBishopPrototypeService implements BishopPrototypeService {
    private final MonitoringService monitoringService;
    private final CommandProcessor commandProcessor;

    @Override
    public void submitCommand(Command command) {
        commandProcessor.submitCommand(command);
    }

    @Override
    public boolean isProcessorBusy() {
        return monitoringService.isProcessorBusy();
    }

    @Override
    public int getQueueSize() {
        return monitoringService.getQueueSize();
    }

    @Override
    public int getAuthorCompleted(String author) {
        return monitoringService.getAuthorCompleted(author);
    }
}
