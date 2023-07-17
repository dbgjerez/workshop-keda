package io.dborrego.client;

import javax.enterprise.context.ApplicationScoped;

import io.dborrego.domain.ParkingDTO;

@ApplicationScoped
public class ParkingClient implements Client<ParkingDTO> {

    @Override
    public ParkingDTO findById(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public ParkingDTO findLastByPlate(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'findLastByPlate'");
    }
    
}
