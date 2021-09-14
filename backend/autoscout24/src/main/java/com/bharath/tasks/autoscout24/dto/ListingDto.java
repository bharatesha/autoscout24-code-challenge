package com.bharath.tasks.autoscout24.dto;

import java.io.Serializable;
import java.util.Objects;

public class ListingDto implements Serializable {

    public static final long serialVersionUID = 1L;

    public Integer ranking;

    public final Long listingId;

    public final String make;

    public final String sellingPrice;

    public final String mileage;

    public final Long totalAmountOfContacts;

    public ListingDto(final Integer ranking, final Long listingId, final String make, final String sellingPrice, final String mileage, final Long totalAmountOfContacts) {
        this.ranking = ranking;
        this.listingId = listingId;
        this.make = make;
        this.sellingPrice = sellingPrice;
        this.mileage = mileage;
        this.totalAmountOfContacts = totalAmountOfContacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingDto that = (ListingDto) o;
        return listingId.equals(that.listingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingId);
    }

    @Override
    public String toString() {
        return "{" +
                "ranking=" + ranking +
                ", listingId=" + listingId +
                ", make='" + make + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", mileage='" + mileage + '\'' +
                ", totalAmountOfContacts=" + totalAmountOfContacts +
                '}';
    }
}
