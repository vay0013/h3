package com.vay.synthetichumancorestarter.util;

import com.vay.synthetichumancorestarter.model.Command;

public interface CommandProcessor {
    void submitCommand(Command command);

    boolean isBusy();

    int getQueueSize();
}
