package edu.java.bot.interceptor;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Log4j2
@RequiredArgsConstructor
@SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
public class RateLimitInterceptor implements HandlerInterceptor {

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private final BucketBuilder bucketBuilder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws IOException {

        String ip = this.getIpFromRequest(request);
        Bucket bucket = buckets.computeIfAbsent(ip, key -> bucketBuilder.getBucket());

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            log.info("Rate Limiting - OK. IP: {} | prode: {}", ip, probe);
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            log.info("Rate Limiting - BAD. IP: {} | prode: {}", ip, probe);
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),
                "You have exhausted your API Request Quota");
            return false;
        }

    }

    private String getIpFromRequest(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            return request.getRemoteAddr();
        }
        return ipAddress.split(",")[0];
    }

}
