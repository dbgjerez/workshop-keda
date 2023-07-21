package io.dborrego.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.dborrego.domain.Fare;
import io.dborrego.domain.FaresRepository;

@ApplicationScoped
public class FaresService {

    @Inject
    FaresRepository faresRepository;

    public List<Fare> listAll(String vehicleType) {
        return faresRepository.listAll(vehicleType);
    }

    public Fare findById(final Long idParking) {
        return faresRepository.findById(idParking);
    }

    @Transactional
    public Fare saveOrUpdate(final Fare f) {
        final Fare fare = Optional.ofNullable(f).map(Fare::getId).map(id -> faresRepository.findById(id))
                .orElse(new Fare());
        fare.setVehicleType(Optional
                .ofNullable(f.getVehicleType())
                .map(String::toLowerCase)
                .orElseThrow());
        fare.setMinutePrice(f.getMinutePrice());
        faresRepository.persist(fare);
        return fare;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return Optional.ofNullable(id).map(idParking -> faresRepository.deleteById(idParking)).orElse(false);
    }

}
