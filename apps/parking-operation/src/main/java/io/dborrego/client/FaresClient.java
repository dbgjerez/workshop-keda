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

    @Override
    public FareDTO create(FareDTO obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(FareDTO obj, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
