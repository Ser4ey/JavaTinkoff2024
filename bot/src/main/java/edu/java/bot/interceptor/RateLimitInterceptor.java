package edu.java.bot.interceptor;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Log4j2
@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private Bucket bucket;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws IOException {
//        String apiKey = request.getHeader("X-api-key");
//        if (apiKey == null || apiKey.isEmpty()) {
//            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing Header: X-api-key");
//            return false;
//        }
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
//        Bucket tokenBucket = pricingPlanService.resolveBucket(apiKey);
//        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            log.info("rate limiting. IP: ? | prode: {}", probe);

            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            log.info("rate limiting. IP: ? | prode: {}", probe);

            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),
                "You have exhausted your API Request Quota");
            return false;
        }
    }
}
