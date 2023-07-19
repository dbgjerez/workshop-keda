package io.dborrego.service;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Logger;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.dborrego.client.FaresClient;
import io.dborrego.client.InvoiceClient;
import io.dborrego.client.ParkingClient;
import io.dborrego.domain.CameraRead;
import io.dborrego.domain.FareDTO;
import io.dborrego.domain.InvoiceDTO;
import io.dborrego.domain.ParkingDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ParkingOperationService {

    private static final Logger log = Logger.getLogger(ParkingOperationService.class.getName());

    @RestClient
    ParkingClient parkingClient;

    @RestClient
    FaresClient faresClient;

    @Inject
    InvoiceClient invoiceClient;

    public void newReading(CameraRead reading) {
        var lastCarParking = parkingClient.findLastByPlateParked(reading.getPlate(), true);
        if (!lastCarParking.isEmpty() &&
                !lastCarParking.stream().max(Comparator.comparing(p -> p.getEntranceDate()))
                        .map(ParkingDTO::getDepartureDate).isPresent()) {
            var parking = lastCarParking.stream().max(Comparator.comparing(p -> p.getEntranceDate())).get();

            log.info(String.format("The car %s is getting out the parking", parking.getPlate()));
            var minutes = parkingMinutes(parking.getEntranceDate(), reading.getDate());
            var total = invoiceTotalPrice(minutes, parking.getVehicleType());
            var invoice = createInvoice(parking, total);
            parking.setDepartureDate(reading.getDate());
            parkingClient.update(parking, parking.getId());
            invoiceClient.createInvoice(invoice);
        } else {
            // The car is entering in the parking
            log.info(String.format("The car %s is going into the parking", reading.getPlate()));
            var p = createParking(reading.getDate(),
                    reading.getPlate(),
                    reading.getType());
            parkingClient.create(p);
        }
    }

    private ParkingDTO createParking(Date date, String plate, String vehicleType) {
        final ParkingDTO parking = new ParkingDTO();
        parking.setEntranceDate(date);
        parking.setPlate(plate);
        parking.setVehicleType(vehicleType);
        return parking;
    }

    private InvoiceDTO createInvoice(ParkingDTO parking, Float total) {
        final InvoiceDTO invoice = new InvoiceDTO();
        invoice.setId(parking.getId());
        invoice.setAmount(total);
        invoice.setInvoiceDate(new Date());
        invoice.setPlate(parking.getPlate());
        return invoice;
    }

    private Float invoiceTotalPrice(Integer minutes, String vehicleType) {
        var fares = faresClient.getByVehicleType(vehicleType);
        return fares.stream()
                .findAny() // Only one active fare
                .map(FareDTO::getMinutePrice)
                .map(price -> price * minutes)
                .orElseThrow(
                        () -> new RuntimeException(String.format("Fare for vehicleType = %s not found", vehicleType)));
    }

    private Integer parkingMinutes(final Date enterDate, final Date outDate) {
        var in = enterDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var out = outDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Math.toIntExact(Math.abs(Duration.between(in, out).toMinutes()));
    }

}
