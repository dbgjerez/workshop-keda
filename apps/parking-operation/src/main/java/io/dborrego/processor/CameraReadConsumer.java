package io.dborrego.processor;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.dborrego.client.ParkingClient;
import io.dborrego.domain.CameraRead;
import io.dborrego.service.ParkingOperationService;

@ApplicationScoped
public class CameraReadConsumer {

    private static final Logger LOGGER = Logger.getLogger(CameraReadConsumer.class.getName());

    @Inject
    ParkingOperationService service;

    @Incoming("camera")
    @Transactional
    public void update(final CameraRead reading) {

        if (reading != null) {
            LOGGER.info(String.format("Received %s", reading));
        }
    }

}