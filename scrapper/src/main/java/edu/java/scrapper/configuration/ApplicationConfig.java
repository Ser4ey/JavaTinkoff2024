package edu.java.scrapper.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @Bean
    @NotNull
    Scheduler scheduler,
    String databaseAccessType,
    boolean useQueue,
    RetryConfig retry,
    KafkaConfig kafka

) {
    public record Scheduler(
        boolean enable,
        @NotNull
        Duration interval,
        @NotNull
        Duration forceCheckDelay,
        int checkedLinksBatchSize
    ) {
    }

    public record RetryConfig(
        @NotEmpty
        @Pattern(regexp = "^(fixed|linear|exponential)$", message = "Invalid strategy name")
        String strategyName,
        @Positive
        int retryNumbers,
        @Positive
        long interval,
        @Positive
        float multiplier,
        List<String> retryCodes
    ) {
    }

    public record KafkaConfig(
        @NotEmpty
        String topic,
        @NotEmpty
        String bootstrapServers
    ) {
    }
}
