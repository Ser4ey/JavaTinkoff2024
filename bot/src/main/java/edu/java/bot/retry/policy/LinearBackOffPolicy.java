package edu.java.bot.retry.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.ThreadWaitSleeper;


@RequiredArgsConstructor
public class LinearBackOffPolicy implements BackOffPolicy {

    private final Sleeper sleeper = new ThreadWaitSleeper();
    private final long initialInterval;

    @Override
    public BackOffContext start(RetryContext context) {
        return new LinearBackOffContext();
    }

    @Override
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        LinearBackOffContext context = (LinearBackOffContext) backOffContext;
//        System.out.println("Спим: " + context.currentStep * initialInterval);
        try {
            sleeper.sleep(context.getCurrentStep() * initialInterval);
        } catch (InterruptedException e) {
            throw new BackOffInterruptedException("Thread was interrupted while sleeping", e);
        }
        context.setCurrentStep(context.getCurrentStep() + 1);
    }


    @Setter
    @Getter
    private static class LinearBackOffContext implements BackOffContext {
        private long currentStep;

        LinearBackOffContext() {
            this.currentStep = 1;
        }

    }
}
