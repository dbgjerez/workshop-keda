package io.dborrego.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {

    private String type;
    private Long id;
    private String title;
    private Integer quantity;
    
}