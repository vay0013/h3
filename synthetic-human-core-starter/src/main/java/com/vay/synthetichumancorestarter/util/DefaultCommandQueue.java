package com.vay.synthetichumancorestarter.util;

import com.vay.synthetichumancorestarter.exception.CommandQueueOverflowException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class DefaultCommandQueue implements CommandQueue {
    @Value("${threadPoolExecutor.maxSize}")
    private int maxSize;

    private ThreadPoolExecutor executor;

    @PostConstruct
    private void init() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxSize);
    }

    @Override
    public void submit(Runnable command) {
        if (executor.getQueue().size() >= maxSize) {
            throw new CommandQueueOverflowException("queue is full");
        }
        executor.submit(command);
    }

    @Override
    public int getQueueSize() {
        return executor.getQueue().size();
    }
}
