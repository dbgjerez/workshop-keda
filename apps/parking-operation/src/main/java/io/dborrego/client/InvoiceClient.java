package io.dborrego.client;

import javax.enterprise.context.ApplicationScoped;

import io.dborrego.domain.InvoiceDTO;

@ApplicationScoped
public class InvoiceClient implements Client<InvoiceDTO> {

    @Override
    public InvoiceDTO findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public InvoiceDTO create(InvoiceDTO obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(InvoiceDTO obj, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    
}
