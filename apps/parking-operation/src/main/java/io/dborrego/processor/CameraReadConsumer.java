package io.dborrego.processor;

import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.dborrego.domain.CameraRead;
import io.dborrego.service.ParkingOperationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CameraReadConsumer {

    private static final Logger LOGGER = Logger.getLogger(CameraReadConsumer.class.getName());

    @Inject
    ParkingOperationService service;

    @Incoming("camera")
    @Transactional
    public void update(final CameraRead reading) {
        LOGGER.info(String.format("Received %s", reading));
        service.newReading(reading);
    }

}