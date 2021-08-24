package com.bharath.tasks.autoscout24.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private final Long listingId;

    @NotEmpty
    private final Instant contactDate;

    public Contact(final Long listingId, final Instant contactDate) {
        this.listingId = listingId;
        this.contactDate = contactDate;
    }

    public Long getListingId() {
        return listingId;
    }

    public Instant getContactDate() {
        return contactDate;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    } // Duplicate can exists, so no objects are equal

    @Override
    public int hashCode() {
        return Objects.hash(listingId, contactDate);
    }
}
