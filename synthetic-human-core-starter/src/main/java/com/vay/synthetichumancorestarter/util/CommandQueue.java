package com.vay.synthetichumancorestarter.util;

public interface CommandQueue {
    void submit(Runnable command);

    int getQueueSize();
}
