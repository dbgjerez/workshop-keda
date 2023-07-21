package io.dborrego.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class FaresRepository implements PanacheRepository<Fare> {

    public List<Fare> listAll(String vehicleType) {
        return streamAll()
                .filter(Optional.ofNullable(vehicleType).isPresent()
                        ? f -> f.getVehicleType().equals(vehicleType.toLowerCase())
                        : f -> true)
                .collect(Collectors.toList());
    }

}
