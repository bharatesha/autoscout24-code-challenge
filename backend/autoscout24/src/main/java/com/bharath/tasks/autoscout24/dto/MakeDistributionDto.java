package com.bharath.tasks.autoscout24.dto;

import java.io.Serializable;
import java.util.Objects;

public class MakeDistributionDto implements Serializable {

    public static final long serialVersionUID = 1L;

    public final String make;

    public final String distributionPercent;

    public MakeDistributionDto(String make, String distributionPercent) {
        this.make = make;
        this.distributionPercent = distributionPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MakeDistributionDto that = (MakeDistributionDto) o;
        return make.equals(that.make);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make);
    }

    @Override
    public String toString() {
        return "{" +
                "make='" + make + '\'' +
                ", distributionPercent='" + distributionPercent + '\'' +
                '}';
    }
}
