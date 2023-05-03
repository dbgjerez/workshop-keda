package io.dborrego.processor;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.dborrego.domain.dto.Stock;

@ApplicationScoped
public class StockConsumer {

    private static final Logger LOGGER = Logger.getLogger(StockConsumer.class.getName());

    @Incoming("stock")
    @Transactional
    public void update(final Stock stock) {
        LOGGER.info(String.format("Received %s", stock));
    }

}