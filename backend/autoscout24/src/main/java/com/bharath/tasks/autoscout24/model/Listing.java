package com.bharath.tasks.autoscout24.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Listing implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private final Long id;

    @NotBlank
    private final String make;

    @NotBlank
    private final BigDecimal price;

    @NotBlank
    private final Integer mileage;

    @NotBlank
    private final String sellerType;

    public Listing(final Long id, final String make, final BigDecimal price, final Integer mileage, final String sellerType){

        this.id = id;
        this.make = make;
        this.price = price;
        this.mileage = mileage;
        this.sellerType = sellerType;
    }

    public Long getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getMileage() {
        return mileage;
    }

    public String getSellerType() {
        return sellerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Listing listing = (Listing) o;
        return id == listing.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", price=" + price +
                ", mileage=" + mileage +
                ", sellerType='" + sellerType + '\'' +
                '}';
    }
}
