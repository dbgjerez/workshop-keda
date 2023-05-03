package io.dborrego.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue
    private Long idBook;
    private String title;
    private Integer stock = 0;
    
}