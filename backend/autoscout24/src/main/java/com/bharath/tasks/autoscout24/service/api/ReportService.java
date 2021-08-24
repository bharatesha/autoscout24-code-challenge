package com.bharath.tasks.autoscout24.service.api;

import com.bharath.tasks.autoscout24.dto.AvgListingPriceDto;
import com.bharath.tasks.autoscout24.dto.ListingsPerMonthDto;
import com.bharath.tasks.autoscout24.dto.MakeDistributionDto;
import com.bharath.tasks.autoscout24.dto.PriceDto;
import com.bharath.tasks.autoscout24.model.Listing;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ReportService {

    List<AvgListingPriceDto> getAvgListingPrice(List<Listing> listingsList);

    List<MakeDistributionDto> getMakeDistributionPercent(List<Listing> listingList);

    PriceDto getAvg30PercentMostContactedListingsPrice(Map<Long, Listing> listingsMap, Map<Long, List<Instant>> contactListings);

    List<ListingsPerMonthDto> getTop5ListingsPerMonth(Map<Long, Listing> listingsMap, Map<Long, List<Instant>> contactListings);

}
