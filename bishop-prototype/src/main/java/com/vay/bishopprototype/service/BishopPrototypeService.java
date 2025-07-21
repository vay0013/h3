package com.vay.bishopprototype.service;

import com.vay.synthetichumancorestarter.model.Command;

public interface BishopPrototypeService {
    void submitCommand(Command command);

    boolean isProcessorBusy();

    int getQueueSize();

    int getAuthorCompleted(String author);
}
