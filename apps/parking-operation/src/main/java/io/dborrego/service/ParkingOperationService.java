package io.dborrego.service;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.dborrego.client.FaresClient;
import io.dborrego.client.InvoiceClient;
import io.dborrego.client.ParkingClient;
import io.dborrego.domain.CameraRead;
import io.dborrego.domain.FareDTO;
import io.dborrego.domain.InvoiceDTO;
import io.dborrego.domain.ParkingDTO;

@ApplicationScoped
public class ParkingOperationService {

    @Inject
    ParkingClient parkingClient;

    @Inject
    FaresClient faresClient;

    @Inject
    InvoiceClient invoiceClient;

    public void newCar(CameraRead reading) {
        var lastCarParking = Optional.ofNullable(parkingClient.findLastByPlate(reading.getPlate()));
        if (lastCarParking.isPresent() &&
                lastCarParking.map(ParkingDTO::getDepartureDate).isEmpty()) {
            // The car is getting out of the parking
            var parking = lastCarParking.get();
            var minutes = parkingMinutes(parking.getEntranceDate(), reading.getDate());
            var total = invoiceTotalPrice(minutes, parking.getVehicleType());
            var invoice = createInvoice(parking, total);
            parking.setDepartureDate(reading.getDate());
            parkingClient.update(parking, parking.getId());
            invoiceClient.create(invoice);
        } else {
            // The car is entering in the parking
            var p = createParking(reading.getDate(),
                    reading.getPlate(),
                    reading.getType());
            parkingClient.create(p);
        }
    }

    public ParkingDTO createParking(Date date, String plate, String vehicleType) {
        final ParkingDTO parking = new ParkingDTO();
        parking.setDepartureDate(date);
        parking.setPlate(plate);
        parking.setVehicleType(vehicleType);
        return parking;
    }

    public InvoiceDTO createInvoice(ParkingDTO parking, Float total) {
        final InvoiceDTO invoice = new InvoiceDTO();
        invoice.setId(parking.getId());
        invoice.setAmount(total);
        invoice.setInvoiceDate(new Date());
        invoice.setPlate(parking.getPlate());
        return invoice;
    }

    public Float invoiceTotalPrice(Integer minutes, String vehicleType) {
        var fare = faresClient.findByType(vehicleType);
        return Optional.ofNullable(fare)
                .map(FareDTO::getMinutePrice)
                .map(price -> price * minutes)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Fare for vehicleType = %s not found", vehicleType)));
    }

    public Integer parkingMinutes(final Date enterDate, final Date outDate) {
        var in = enterDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var out = outDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Math.toIntExact(Math.abs(Duration.between(in, out).toMinutes()));
    }

}
