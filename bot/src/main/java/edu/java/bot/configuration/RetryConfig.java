package edu.java.bot.configuration;

import edu.java.bot.exception.request.CustomRequestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
@Log4j2
@SuppressWarnings("MagicNumber")
public class RetryConfig {

    private static final int MAX_RETRY_ATTEMPTS = 5;
    private final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(MAX_RETRY_ATTEMPTS);
    private final NeverRetryPolicy neverRetryPolicy = new NeverRetryPolicy();

//    @Bean
//    public RetryTemplate retryTemplate() {
//        RetryTemplate retryTemplate = new RetryTemplate();
//
//        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
//        fixedBackOffPolicy.setBackOffPeriod(2000l);
//        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
//
//        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
//        retryPolicy.setMaxAttempts(5);
//        retryTemplate.setRetryPolicy(retryPolicy);
//
//        return retryTemplate;
//    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000L);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

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

        return switch (customRequestException.getApiErrorResponse().code()) {
            case "400", "500" -> {
                log.debug("Код ответа {}. Отправляем повторные запросы.",
                        customRequestException.getApiErrorResponse().code());
                yield simpleRetryPolicy;
            }
            default -> {
                log.debug("Код ответа {}. Не отправляем повторные запросы.",
                        customRequestException.getApiErrorResponse().code());
                yield neverRetryPolicy;
            }
        };

    }

}

