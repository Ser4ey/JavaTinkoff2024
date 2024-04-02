package edu.java.bot.interceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitingConfiguration {

    @Value(value = "${bucket.limit-per-minute}")
    private int bucketLimit;

    @Bean
    public Bucket bucket() {
        Refill refill = Refill.intervally(bucketLimit, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(bucketLimit, refill);

        return Bucket.builder().addLimit(limit).build();
    }
}
