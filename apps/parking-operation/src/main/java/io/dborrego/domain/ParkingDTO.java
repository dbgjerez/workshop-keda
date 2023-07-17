package io.dborrego.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ParkingDTO {

    private Long id;
    private String plate;
    private String vehicleType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date entranceDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date departureDate;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlate() {
        return this.plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Date getEntranceDate() {
        return this.entranceDate;
    }

    public void setEntranceDate(Date entranceDate) {
        this.entranceDate = entranceDate;
    }

    public Date getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

}
