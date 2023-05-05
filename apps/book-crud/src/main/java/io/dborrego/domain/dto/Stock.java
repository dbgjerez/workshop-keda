package io.dborrego.domain.dto;

public class Stock {

    private String type;
    private Long id;
    private String title;
    private Integer quantity;

    public Stock() {
    }

    public Stock(String type, Long id, String title, Integer quantity) {
        this.type = type;
        this.id = id;
        this.title = title;
        this.quantity = quantity;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}