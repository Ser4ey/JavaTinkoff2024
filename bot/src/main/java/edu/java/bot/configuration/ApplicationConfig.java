package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    RetryConfig retry,
    KafkaConfig kafka

) {
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
        String bootstrapServers,
        @NotEmpty
        String groupId
    ) {
    }
}
