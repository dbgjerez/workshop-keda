package io.dborrego.domain.dto;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class StockDeserializer extends ObjectMapperDeserializer<Stock> {

    public StockDeserializer(Class<Stock> type) {
        super(Stock.class);
    }
    
}
