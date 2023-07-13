package io.dborrego.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import io.dborrego.domain.Fares;
import io.dborrego.domain.FaresRepository;

@ApplicationScoped
public class FaresService {

    @Inject
    FaresRepository faresRepository;

    public List<Fares> listAll() {
        return faresRepository.listAll();
    }

    public Fares findById(final Long idParking) {
        return faresRepository.findById(idParking);
    }

    @Transactional
    public Fares saveOrUpdate(final Fares f) {
        final Fares fare = Optional.ofNullable(f).map(Fares::getId).map(id -> faresRepository.findById(id))
                .orElse(new Fares());
        fare.setVehicleType(f.getVehicleType());
        fare.setMinutePrice(f.getMinutePrice());
        faresRepository.persist(fare);
        return fare;
    }

    @Transactional
    public Boolean deleteById(final Long id) {
        return Optional.ofNullable(id).map(idParking -> faresRepository.deleteById(idParking)).orElse(false);
    }

}
