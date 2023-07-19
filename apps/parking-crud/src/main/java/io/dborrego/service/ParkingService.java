package io.dborrego.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.dborrego.domain.Parking;
import io.dborrego.domain.ParkingRepository;

@ApplicationScoped
public class ParkingService {

    @Inject
    ParkingRepository parkingRepository;

    public List<Parking> listAll(String plate, Boolean parked) {
        return parkingRepository.listAllByPlate(plate, parked);
    }

    public Parking findById(final Long idParking) {
        return parkingRepository.findById(idParking);
    }

    @Transactional
    public Parking saveOrUpdate(final Parking p) {
        final Parking parking = Optional.ofNullable(p).map(Parking::getId).map(id -> parkingRepository.findById(id))
                .orElse(new Parking());
        parking.setDepartureDate(p.getDepartureDate());
        parking.setEntranceDate(p.getEntranceDate());
        parking.setPlate(p.getPlate());
        parking.setVehicleType(p.getVehicleType());
        parkingRepository.persist(parking);
        return parking;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return Optional.ofNullable(id).map(idParking -> parkingRepository.deleteById(idParking)).orElse(false);
    }

}
