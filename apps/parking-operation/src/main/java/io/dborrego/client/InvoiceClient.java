package io.dborrego.client;

import io.dborrego.domain.InvoiceDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;

@ApplicationScoped
public class InvoiceClient {

    @GET
    public InvoiceDTO createInvoice(InvoiceDTO invoice) {
        // TODO send to Kafka
        return invoice;
    }

}
