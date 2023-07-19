package io.dborrego.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ParkingRepository implements PanacheRepository<Parking> {

    public List<Parking> listAllByPlate(String plate, Boolean parked) {
        return streamAll()
                .filter(Optional.ofNullable(plate).isPresent()
                        ? p -> p.getPlate().equals(plate)
                        : p -> true)
                .filter(Optional.ofNullable(parked).isPresent() && parked
                        ? p -> p.getDepartureDate() == null
                        : p -> true)
                .collect(Collectors.toList());
    }

}
