package com.proxidize_assienment.length.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

@RestController
public class LengthController {

    private final Timer requestTimer;
    private final Counter errorCounter;
    private final Counter requestCounter;

    public LengthController(MeterRegistry registry) {
        this.requestTimer = Timer.builder("app.requests.seconds")
            .description("Request processing time")
            .tag("service", "length")
            .register(registry);
        
        this.errorCounter = Counter.builder("app.errors.total")
            .description("Total calculation errors")
            .tag("service", "length")
            .register(registry);

        this.requestCounter = Counter.builder("app.requests.total")
            .description("Total requests processed")
            .tag("service", "length")
            .register(registry);
    }

    @PostMapping(value = "/length", produces = MediaType.TEXT_PLAIN_VALUE)
    public String computeLength(@RequestBody String text) {
        requestCounter.increment();
        long start = System.nanoTime();
        
        try {
            String processed = text;
            // Remove exactly one pair of surrounding quotes if present
            if (processed.startsWith("\"") && processed.endsWith("\"")) {
                processed = processed.substring(1, processed.length() - 1);
            }
            
            // Remove all spaces
            processed = processed.replaceAll(" ", "");
            
            return processed.length() + "\n";
        } catch (Exception e) {
            errorCounter.increment();
            throw e;
        } finally {
            long duration = System.nanoTime() - start;
            requestTimer.record(duration, TimeUnit.NANOSECONDS);
        }
    }
}