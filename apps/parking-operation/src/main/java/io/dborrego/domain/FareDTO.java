package io.dborrego.domain;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class FareDTO {
    private Long id;
    private String vehicleType;
    private Float minutePrice;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Float getMinutePrice() {
        return this.minutePrice;
    }

    public void setMinutePrice(Float minutePrice) {
        this.minutePrice = minutePrice;
    }
}
