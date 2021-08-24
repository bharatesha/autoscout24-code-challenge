package com.bharath.tasks.autoscout24.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ListingsPerMonthDto implements Serializable {

    public static final long serialVersionUID = 1L;

    public final String monthYear;

    public final List<ListingDto> listings;

    public ListingsPerMonthDto(String monthYear, List<ListingDto> listings) {
        this.monthYear = monthYear;
        this.listings = listings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListingsPerMonthDto that = (ListingsPerMonthDto) o;
        return monthYear.equals(that.monthYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monthYear);
    }

    @Override
    public String toString() {
        return "{" +
                "monthYear='" + monthYear + '\'' +
                ", listings=" + listings +
                '}';
    }
}
