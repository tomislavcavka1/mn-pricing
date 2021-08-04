package com.danielprinz.udemy.quotes.external;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
@Requires(notEnv = Environment.TEST)    // event scheduler will not be injected into micronaut app context during tests
public class EventScheduler {

    private static final Logger                LOG     = LoggerFactory.getLogger(EventScheduler.class);
    private static final List<String>          SYMBOLS = Arrays.asList("AAPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX");
    private final        ExternalQuoteProducer externalQuoteProducer;

    public EventScheduler(final ExternalQuoteProducer externalQuoteProducer) {
        this.externalQuoteProducer = externalQuoteProducer;
    }

    @Scheduled(fixedDelay = "10s")
    void generate() {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final ExternalQuote quote = new ExternalQuote(
                SYMBOLS.get(random.nextInt(0, SYMBOLS.size() - 1)),
                randomValue(random),
                randomValue(random)
        );
        LOG.debug("Generate external quote {}", quote);
        externalQuoteProducer.send(quote.getSymbol(), quote);
    }

    private BigDecimal randomValue(final ThreadLocalRandom random) {
        return BigDecimal.valueOf(random.nextDouble(0, 1000));
    }
}
