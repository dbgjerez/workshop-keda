package io.dborrego.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;

@Data
public class Stock {

    private String type;
    private Long id;
    private String title;
    private Integer quantity;

    @JsonCreator
    public Stock() {
    }
    
}