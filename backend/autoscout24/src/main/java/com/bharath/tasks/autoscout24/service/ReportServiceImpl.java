package com.bharath.tasks.autoscout24.service;

import com.bharath.tasks.autoscout24.dto.*;
import com.bharath.tasks.autoscout24.model.Contact;
import com.bharath.tasks.autoscout24.model.Listing;
import com.bharath.tasks.autoscout24.service.api.ReportService;
import com.bharath.tasks.autoscout24.util.DateUtil;
import com.bharath.tasks.autoscout24.util.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final TransformationService transformationService;

    @Autowired
    ReportServiceImpl(final TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    public List<AvgListingPriceDto> getAvgListingPrice(List<Listing> listingsList) {

        final Map<String, BigDecimal> avgListingPriceSumMap = new HashMap<>();

        final Map<String, Long> sellertTypePriceCountMap = new HashMap<>();

        // Calculate sum and count by seller type
        listingsList.forEach(listing -> {
            Long count = sellertTypePriceCountMap.getOrDefault(listing.getSellerType(), 0L);
            sellertTypePriceCountMap.put(listing.getSellerType(), count + 1);

            BigDecimal avgSum = avgListingPriceSumMap.getOrDefault(listing.getSellerType(), new BigDecimal(0));
            avgListingPriceSumMap.put(listing.getSellerType(), avgSum.add(listing.getPrice()));
        });

        // Calculate and format average price by seller type
        return avgListingPriceSumMap.entrySet().stream()
                .peek(sellerTypeSumEntry -> sellerTypeSumEntry.setValue(calculateAvgPrice(sellerTypeSumEntry, sellertTypePriceCountMap)))
                .map(sellerTypeAvgEntry -> new AvgListingPriceDto(sellerTypeAvgEntry.getKey(), FormatUtils.formatCurrency(sellerTypeAvgEntry.getValue())))
                .collect(Collectors.toList());
    }

    private BigDecimal calculateAvgPrice(Map.Entry<String, BigDecimal> sellerTypeSumEntry, Map<String, Long> sellertTypePriceCountMap) {
        return sellerTypeSumEntry.getValue().divide(new BigDecimal(sellertTypePriceCountMap.get(sellerTypeSumEntry.getKey())), RoundingMode.CEILING);
    }

    public List<MakeDistributionDto> getMakeDistributionPercent(List<Listing> listingList) {
        return listingList.stream()
                .collect(Collectors.groupingBy(Listing::getMake,
                        // Calculate distirbution percent
                        Collectors.collectingAndThen(Collectors.counting(),
                                makeCount -> getDistributionPercent(makeCount, listingList.size())
                        )
                ))
                // Sort by distribution percent value
                .entrySet().stream().sorted(FormatUtils.MAKE_PERCENT_COMPARATOR)
                .map(makeDistributionEntry -> new MakeDistributionDto(makeDistributionEntry.getKey(), makeDistributionEntry.getValue()))
                .collect(Collectors.toList());
    }

    private String getDistributionPercent(Long makeCount, int totalCount) {
        Long percent = (makeCount * 100) / totalCount;
        return FormatUtils.formatPercent(percent);
    }

    public PriceDto getAvg30PercentMostContactedListingsPrice(final Map<Long, Listing> listingsMap, final Map<Long, List<Instant>> contactListings) {

        long listings30PercentContactedSize = Math.round(contactListings.size() * 0.3);

        //Calculate first 30 percent most contacted listings average price
        List<BigDecimal> listingsPriceMorethan30List = contactListings.entrySet()
                .stream()
                .filter(contactListEntry -> contactListEntry.getValue().size() >= listings30PercentContactedSize)   //.limit(listings30PercentContactedSize) //TODO: clarification on problem statement
                .map(cl -> listingsMap.get(cl.getKey()).getPrice())
                .collect(Collectors.toList());

        BigDecimal avgPriceBD = listingsPriceMorethan30List.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(listingsPriceMorethan30List.size()), RoundingMode.CEILING);

        //Format and return result
        return new PriceDto(FormatUtils.formatCurrency(avgPriceBD));
    }

    public List<ListingsPerMonthDto> getTop5ListingsPerMonth(Map<Long, Listing> listingsMap, final Map<Long, List<Instant>> contactListings) {

        //Create contact object list to do group by
        List<Contact> contactList = transformationService.transformToContactList(contactListings);
        return contactList.stream()
                .collect(
                        //Group by month
                        Collectors.groupingBy((contact -> DateUtil.formatDate(contact.getContactDate())), () -> new TreeMap<>(Comparator.comparing(DateUtil::convertToDate)),
                                //group by listings, sort and take top 5
                                Collectors.collectingAndThen(
                                        Collectors.groupingBy(Contact::getListingId, Collectors.counting()),
                                        contactsListingCountMap -> transformAndGetTop5ListingsPerMonth(contactsListingCountMap, listingsMap)
                                )
                        )
                )
                //Convert to dto object
                .entrySet().stream()
                .map(monthListingsEntry -> new ListingsPerMonthDto(monthListingsEntry.getKey(), monthListingsEntry.getValue()))
                .collect(Collectors.toList());
    }

    private List<ListingDto> transformAndGetTop5ListingsPerMonth(Map<Long, Long> contactsListingCountMap, Map<Long, Listing> listingsMap) {
        AtomicInteger runCount = new AtomicInteger(0);
        return contactsListingCountMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(5)
                .map((contactCountEntry) -> transformationService.transformToListingDto(listingsMap, contactCountEntry, runCount.incrementAndGet()))
                .collect(Collectors.toList());
    }

}
