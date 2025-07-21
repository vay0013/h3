package com.vay.synthetichumancorestarter.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendAudit(String topic, String message) {
        kafkaTemplate.send(topic, message);
        log.info("[AUDIT]-kafka topic={}, message={}", topic, message);
    }
}
