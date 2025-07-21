package com.vay.synthetichumancorestarter.service;

import com.vay.synthetichumancorestarter.model.Command;

public interface MonitoringService {

    void incrementAuthorCompleted(String author, Command command);

    boolean isProcessorBusy();

    int getQueueSize();

    int getAuthorCompleted(String author);
}
