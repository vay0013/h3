package com.vay.synthetichumancorestarter.util;

import com.vay.synthetichumancorestarter.kafka.AuditProducer;
import com.vay.synthetichumancorestarter.model.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class WeylandAuditAspect {
    private final AuditProducer auditProducer;

    @Value("${weyland.audit.mode:console}")
    private String auditMode;
    @Value("${weyland.kafka.topic}")
    private String kafkaTopic;

    @Around("@annotation(com.vay.synthetichumancorestarter.util.WeylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();
        Object[] args = joinPoint.getArgs();
        Object result = null;
        Throwable err = null;
        try {
            result = joinPoint.proceed(args);
            return result;
        } catch (Throwable throwable) {
            err = throwable;
            throw err;
        } finally {
            String message;
            if (result instanceof Command cmd) {
                message = "[AUDIT] method=%s, args=%s, result=(description=%s, priority=%s, author=%s, time=%s), error=%s"
                        .formatted(methodName, Arrays.toString(args), cmd.getDescription(), cmd.getPriority(), cmd.getAuthor(), cmd.getTime(), err);
            } else {
                message = "[AUDIT] method=%s, args=%s, result=%s, error=%s".formatted(methodName, Arrays.toString(args), result, err);
            }
            if ("kafka".equalsIgnoreCase(auditMode)) {
                auditProducer.sendAudit(kafkaTopic, message);
            } else {
                log.info(message);
            }
        }
    }
}
