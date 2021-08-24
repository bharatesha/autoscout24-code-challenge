package com.bharath.tasks.autoscout24.service;

import com.bharath.tasks.autoscout24.dto.ListingDto;
import com.bharath.tasks.autoscout24.model.Contact;
import com.bharath.tasks.autoscout24.model.Listing;
import com.bharath.tasks.autoscout24.util.FormatUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransformationService {

    public ListingDto transformToListingDto(Map<Long, Listing> listingsMap, Map.Entry<Long, Long> totalAmtOfContactsPerListingEntry, int ranking) {
        final Listing listing = listingsMap.get(totalAmtOfContactsPerListingEntry.getKey());
        return new ListingDto(ranking, listing.getId(), listing.getMake(), FormatUtils.formatCurrency(listing.getPrice()), FormatUtils.formatMileage(listing.getMileage()), totalAmtOfContactsPerListingEntry.getValue());
    }

    public List<Contact> transformToContactList(final Map<Long, List<Instant>> contactListMap) {
        return contactListMap.entrySet().stream()
                .flatMap(entry -> transformToContactList(entry.getKey(), entry.getValue()).stream())
                .collect(Collectors.toList());
    }

    private List<Contact> transformToContactList(Long listingId, List<Instant> contactDate) {
        return contactDate.stream()
                .map(date -> new Contact(listingId, date))
                .collect(Collectors.toList());
    }
}
