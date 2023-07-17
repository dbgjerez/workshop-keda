package io.dborrego.service;

import java.util.Date;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.dborrego.client.FaresClient;
import io.dborrego.client.ParkingClient;
import io.dborrego.domain.CameraRead;
import io.dborrego.domain.FareDTO;
import io.dborrego.domain.ParkingDTO;

@ApplicationScoped
public class ParkingOperationService {

    @Inject
    ParkingClient parkingClient;

    @Inject
    FaresClient faresClient;

    public void newCar(CameraRead reading) {
        var lastCarParking = Optional.ofNullable(parkingClient.findLastByPlate(reading.getPlate()));
        if (lastCarParking.isPresent() &&
                lastCarParking.map(ParkingDTO::getDepartureDate).isEmpty()) {
            // The car is getting out of the parking
            var parking = lastCarParking.get();
            var minutes = parkingMinutes(parking.getEntranceDate(), reading.getDate());
            var total = invoiceTotalPrice(minutes, parking.getVehicleType());
        } else {
            // The car is entering in the parking

        }
    }

    

    public InvoiceDTO createInvoice () {

    }

    public Float invoiceTotalPrice(Integer minutes, String vehicleType) {
        var fare = faresClient.findByType(vehicleType);
        return Optional.ofNullable(fare)
                .map(FareDTO::getMinutePrice)
                .map(price -> price * minutes)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Fare for vehicleType = %s not found", vehicleType)));
    }

    public Integer parkingMinutes(final Date enterDate, final Date getOutDate) {
        return 0;
    }

}
