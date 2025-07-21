package com.vay.synthetichumancorestarter.service;

import com.vay.synthetichumancorestarter.model.Command;
import com.vay.synthetichumancorestarter.util.CommandProcessor;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DefaultMonitoringService implements MonitoringService {
    private final CommandProcessor processor;
    private final MeterRegistry meterRegistry;
    private final Map<String, Counter> authorCounters = new ConcurrentHashMap<>();

    @Override
    public void incrementAuthorCompleted(String author, Command command) {
        authorCounters.computeIfAbsent(author, a -> Counter.builder("android.tasks.completed")
                .tag("author", a)
                .register(meterRegistry)).increment();
    }

    @Override
    public boolean isProcessorBusy() {
        return processor.isBusy();
    }

    @Override
    public int getQueueSize() {
        return processor.getQueueSize();
    }

    @Override
    public int getAuthorCompleted(String author) {
        return (int) Math.round(authorCounters.getOrDefault(author, Counter.builder("android.tasks.completed")
                .tag("author", author)
                .register(meterRegistry)).count());
    }
}
