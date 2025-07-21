package com.vay.synthetichumancorestarter.service;

import com.vay.synthetichumancorestarter.model.Command;

public interface MonitoringService {

    void incrementAuthorCompleted(String author, Command command);

    int getAuthorCompleted(String author);
}
