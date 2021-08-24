package com.bharath.tasks.autoscout24.dto;

import java.io.Serializable;
import java.util.Objects;

public class AvgListingPriceDto implements Serializable {

    public static final long serialVersionUID = 1L;

    public final String sellerType;

    public final String averagePrice;

    public AvgListingPriceDto(final String sellerType, final String averagePrice){
        this.sellerType = sellerType;
        this.averagePrice = averagePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvgListingPriceDto that = (AvgListingPriceDto) o;
        return sellerType.equals(that.sellerType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sellerType);
    }

    @Override
    public String toString() {
        return "{" +
                "sellerType='" + sellerType + '\'' +
                ", averagePrice='" + averagePrice + '\'' +
                '}';
    }
}
