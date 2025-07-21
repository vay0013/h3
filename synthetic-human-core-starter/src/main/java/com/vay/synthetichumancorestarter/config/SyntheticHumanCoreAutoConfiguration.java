package com.vay.synthetichumancorestarter.config;

import com.vay.synthetichumancorestarter.kafka.AuditProducer;
import com.vay.synthetichumancorestarter.service.DefaultMonitoringService;
import com.vay.synthetichumancorestarter.service.MonitoringService;
import com.vay.synthetichumancorestarter.util.*;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@EnableAspectJAutoProxy
@ConditionalOnProperty(prefix = "weyland", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SyntheticHumanCoreAutoConfiguration {

    @Bean
    public CommandQueue commandQueue() {
        return new DefaultCommandQueue();
    }

    @Bean
    public CommandProcessor commandProcessor(CommandQueue commandQueue, MonitoringService monitoringService) {
        return new DefaultCommandProcessor(commandQueue, monitoringService);
    }

    @Bean
    public MonitoringService monitoringService(MeterRegistry meterRegistry) {
        return new DefaultMonitoringService(meterRegistry);
    }
    
    @Bean
    @ConditionalOnProperty(prefix = "weyland.audit", name = "mode", havingValue = "kafka")
    public AuditProducer auditProducer(KafkaTemplate<String, String> kafkaTemplate) {
        return new AuditProducer(kafkaTemplate);
    }
    
    @Bean
    public WeylandAuditAspect weylandAuditAspect(AuditProducer auditProducer) {
        return new WeylandAuditAspect(auditProducer);
    }
} 