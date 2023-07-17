package io.dborrego.domain;

import java.time.LocalDateTime;
import java.util.Date;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CameraRead {

    private String plate;
    private String type;
    private Date date;

    public String getPlate() {
        return this.plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}