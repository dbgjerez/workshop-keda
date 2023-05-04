package io.dborrego.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;

@Data
@Entity
@Table
public class Book {

    @Id
    @GeneratedValue
    private Long idBook;
    private String title;
    private Integer stock = 0;

    @JsonCreator
    public Book() {
    }
    
}