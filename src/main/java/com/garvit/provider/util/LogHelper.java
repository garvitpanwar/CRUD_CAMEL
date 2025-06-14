// src/main/java/com/garvit/provider/util/LogHelper.java
package com.garvit.provider.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Named("logHelper")
@ApplicationScoped
public class LogHelper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    public String log(String message, Map<String, Object> headers) {
        String correlationId = (String) headers.getOrDefault("X-Correlation-ID", "N/A");
        String timestamp = (String) headers.getOrDefault("LogTimestamp", FORMATTER.format(ZonedDateTime.now()));
        return String.format("[%s] [CorrelationID: %s] %s", timestamp, correlationId, message);
    }
}
