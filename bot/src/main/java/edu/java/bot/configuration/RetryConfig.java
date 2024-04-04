package edu.java.bot.configuration;

import edu.java.bot.exception.request.CustomRequestException;
import edu.java.bot.retry.policy.LinearBackOffPolicy;
import lombok.extern.log4j.Log4j2;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
@Log4j2
public class RetryConfig {
    private final ApplicationConfig applicationConfig;
    private final SimpleRetryPolicy simpleRetryPolicy;
    private final NeverRetryPolicy neverRetryPolicy = new NeverRetryPolicy();

    public RetryConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
        this.simpleRetryPolicy = new SimpleRetryPolicy(applicationConfig.retry().retryNumbers());
    }

    private BackOffPolicy getBackOffPolicy() {
        switch (applicationConfig.retry().strategyName()) {
            case "fixed" -> {
                FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
                fixedBackOffPolicy.setBackOffPeriod(applicationConfig.retry().interval());
                return fixedBackOffPolicy;
            }
            case "linear" -> {
                return new LinearBackOffPolicy(applicationConfig.retry().interval());
            }
            case "exponential" -> {
                ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
                exponentialBackOffPolicy.setInitialInterval(applicationConfig.retry().interval());
                exponentialBackOffPolicy.setMultiplier(applicationConfig.retry().multiplier());
                return exponentialBackOffPolicy;
            }
            default -> throw new IllegalArgumentException(
                "Invalid retry strategy: " + applicationConfig.retry().strategyName()
            );

        }
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        retryTemplate.setBackOffPolicy(getBackOffPolicy());

        ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
        policy.setExceptionClassifier(configureStatusCodeBasedRetryPolicy());
        retryTemplate.setRetryPolicy(policy);

        return retryTemplate;
    }

    private Classifier<Throwable, RetryPolicy> configureStatusCodeBasedRetryPolicy() {
        return throwable -> {
            if (throwable instanceof CustomRequestException) {
                return getRetryPolicyForCustomRequestException((CustomRequestException) throwable);
            }
            return simpleRetryPolicy;
        };
    }

    private RetryPolicy getRetryPolicyForCustomRequestException(CustomRequestException customRequestException) {
        log.debug("Ответ от сервера: {}", customRequestException.getApiErrorResponse());
        String responseCode = customRequestException.getApiErrorResponse().code();

        if (applicationConfig.retry().retryCodes().contains(responseCode)) {
            log.debug("Код ответа {} входит в список retryCodes. Отправляем повторные запросы.", responseCode);
            return simpleRetryPolicy;
        } else {
            log.debug("Код ответа {} не входит в список retryCodes. Не отправляем повторные запросы.", responseCode);
            return neverRetryPolicy;
        }

    }

}

