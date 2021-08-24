package com.bharath.tasks.autoscout24.dto;

import java.io.Serializable;
import java.util.Objects;

public class PriceDto implements Serializable {

    public static final long serialVersionUID = 1L;

    public final String price;

    public PriceDto(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceDto priceDto = (PriceDto) o;
        return price.equals(priceDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    @Override
    public String toString() {
        return "{" +
                "price='" + price + '\'' +
                '}';
    }
}
