package io.dborrego.domain;

public class Stock {

    private String type;
    private String id;
    private String title;
    private Integer quantity;
    private Float price;

    public Stock() {
    }


    public Stock(String type, String id, String title, Integer quantity, Float price) {
        this.type = type;
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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