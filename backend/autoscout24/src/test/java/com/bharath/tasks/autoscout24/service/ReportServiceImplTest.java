package com.bharath.tasks.autoscout24.service;

import com.bharath.tasks.autoscout24.dto.PriceDto;
import com.bharath.tasks.autoscout24.model.Listing;
import com.bharath.tasks.autoscout24.service.api.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ReportServiceImplTest {

    CsvReadFileService csvReadFileService = new CsvReadFileService();

    ReportService reportService = new ReportServiceImpl(new TransformationService());

    @Test
    public void testGetAvgListingPrice() {

        List<Listing> listings = csvReadFileService.getListingsList();
        String result = reportService.getAvgListingPrice(listings).toString();
        Assertions.assertEquals("[{sellerType='private', averagePrice='€ 26.081,-'}, {sellerType='other', averagePrice='€ 25.318,-'}, {sellerType='dealer', averagePrice='€ 25.038,-'}]", result);

    }

    @Test
    public void testGetMakeDistribution() {
        List<Listing> listings = csvReadFileService.getListingsList();
        String result = reportService.getMakeDistributionPercent(listings).toString();
        String expected = "[{make='Mercedes-Benz', distributionPercent='16%'}, {make='Toyota', distributionPercent='16%'}, {make='Audi', distributionPercent='14%'}, {make='Renault', distributionPercent='14%'}, {make='Mazda', distributionPercent='13%'}, {make='VW', distributionPercent='10%'}, {make='Fiat', distributionPercent='9%'}, {make='BWM', distributionPercent='7%'}]";
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testGetAvg30PercentMostContactedListingsPrice() {
        Map<Long, Listing> listingsMap = csvReadFileService.getListingIdMap();
        Map<Long, List<Instant>> contactListings = csvReadFileService.getContactListingsMap();
        PriceDto result = reportService.getAvg30PercentMostContactedListingsPrice(listingsMap, contactListings);
        String expected = "{price='€ 24.908,-'}";
        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void testGetTop5ListingsPerMonth() {
        Map<Long, Listing> listingsMap = csvReadFileService.getListingIdMap();
        Map<Long, List<Instant>> contactListings = csvReadFileService.getContactListingsMap();
        String result = reportService.getTop5ListingsPerMonth(listingsMap, contactListings).toString();
        String expected = "[{monthYear='01.2020', listings=[{ranking=1, listingId=1061, make='Renault', sellingPrice='€ 5.641,-', mileage='7000 KM', totalAmountOfContacts=21}, {ranking=2, listingId=1132, make='Mercedes-Benz', sellingPrice='€ 34.490,-', mileage='7000 KM', totalAmountOfContacts=18}, {ranking=3, listingId=1077, make='Mercedes-Benz', sellingPrice='€ 8.007,-', mileage='4000 KM', totalAmountOfContacts=17}, {ranking=4, listingId=1099, make='BWM', sellingPrice='€ 5.914,-', mileage='8500 KM', totalAmountOfContacts=17}, {ranking=5, listingId=1122, make='Audi', sellingPrice='€ 40.481,-', mileage='2000 KM', totalAmountOfContacts=17}]}, {monthYear='02.2020', listings=[{ranking=1, listingId=1271, make='Mercedes-Benz', sellingPrice='€ 47.165,-', mileage='6500 KM', totalAmountOfContacts=37}, {ranking=2, listingId=1138, make='Toyota', sellingPrice='€ 13.986,-', mileage='8000 KM', totalAmountOfContacts=33}, {ranking=3, listingId=1235, make='Mercedes-Benz', sellingPrice='€ 5.847,-', mileage='5500 KM', totalAmountOfContacts=32}, {ranking=4, listingId=1006, make='Renault', sellingPrice='€ 47.446,-', mileage='7500 KM', totalAmountOfContacts=32}, {ranking=5, listingId=1250, make='Renault', sellingPrice='€ 8.446,-', mileage='5000 KM', totalAmountOfContacts=31}]}, {monthYear='03.2020', listings=[{ranking=1, listingId=1061, make='Renault', sellingPrice='€ 5.641,-', mileage='7000 KM', totalAmountOfContacts=31}, {ranking=2, listingId=1181, make='Renault', sellingPrice='€ 8.933,-', mileage='3500 KM', totalAmountOfContacts=30}, {ranking=3, listingId=1235, make='Mercedes-Benz', sellingPrice='€ 5.847,-', mileage='5500 KM', totalAmountOfContacts=29}, {ranking=4, listingId=1258, make='Mazda', sellingPrice='€ 44.776,-', mileage='1000 KM', totalAmountOfContacts=29}, {ranking=5, listingId=1271, make='Mercedes-Benz', sellingPrice='€ 47.165,-', mileage='6500 KM', totalAmountOfContacts=29}]}, {monthYear='04.2020', listings=[{ranking=1, listingId=1181, make='Renault', sellingPrice='€ 8.933,-', mileage='3500 KM', totalAmountOfContacts=37}, {ranking=2, listingId=1118, make='Audi', sellingPrice='€ 38.382,-', mileage='2000 KM', totalAmountOfContacts=33}, {ranking=3, listingId=1006, make='Renault', sellingPrice='€ 47.446,-', mileage='7500 KM', totalAmountOfContacts=29}, {ranking=4, listingId=1123, make='VW', sellingPrice='€ 39.077,-', mileage='7000 KM', totalAmountOfContacts=28}, {ranking=5, listingId=1262, make='Renault', sellingPrice='€ 43.778,-', mileage='8000 KM', totalAmountOfContacts=28}]}, {monthYear='05.2020', listings=[{ranking=1, listingId=1204, make='Toyota', sellingPrice='€ 36.895,-', mileage='3500 KM', totalAmountOfContacts=35}, {ranking=2, listingId=1098, make='Toyota', sellingPrice='€ 11.345,-', mileage='3500 KM', totalAmountOfContacts=32}, {ranking=3, listingId=1298, make='Mazda', sellingPrice='€ 15.989,-', mileage='6500 KM', totalAmountOfContacts=30}, {ranking=4, listingId=1018, make='Renault', sellingPrice='€ 33.165,-', mileage='3000 KM', totalAmountOfContacts=29}, {ranking=5, listingId=1132, make='Mercedes-Benz', sellingPrice='€ 34.490,-', mileage='7000 KM', totalAmountOfContacts=27}]}, {monthYear='06.2020', listings=[{ranking=1, listingId=1258, make='Mazda', sellingPrice='€ 44.776,-', mileage='1000 KM', totalAmountOfContacts=18}, {ranking=2, listingId=1006, make='Renault', sellingPrice='€ 47.446,-', mileage='7500 KM', totalAmountOfContacts=15}, {ranking=3, listingId=1037, make='Fiat', sellingPrice='€ 14.940,-', mileage='7000 KM', totalAmountOfContacts=14}, {ranking=4, listingId=1271, make='Mercedes-Benz', sellingPrice='€ 47.165,-', mileage='6500 KM', totalAmountOfContacts=14}, {ranking=5, listingId=1012, make='Audi', sellingPrice='€ 10.286,-', mileage='3000 KM', totalAmountOfContacts=14}]}]";
        Assertions.assertEquals(expected, result);
    }
}
