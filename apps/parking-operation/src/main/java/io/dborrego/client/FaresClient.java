package io.dborrego.client;

import javax.enterprise.context.ApplicationScoped;

import io.dborrego.domain.FareDTO;

@ApplicationScoped
public class FaresClient implements Client<FareDTO> {

    @Override
    public FareDTO findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public FareDTO findByType(String vehicleType) {
        return null;
    }

}
