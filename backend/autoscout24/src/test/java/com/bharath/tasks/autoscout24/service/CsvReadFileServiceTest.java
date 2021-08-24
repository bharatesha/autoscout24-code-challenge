package com.bharath.tasks.autoscout24.service;

import com.bharath.tasks.autoscout24.model.Contact;
import com.bharath.tasks.autoscout24.model.Listing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class CsvReadFileServiceTest {


    CsvReadFileService csvReadFileService = new CsvReadFileService();

    @Test
    public void whenListingCsvFileCorrect_thenParseSuccessful() throws Exception{
        List<Listing> listings = csvReadFileService.getListingsList();
        Assertions.assertEquals(300, listings.size());
    }

    @Test
    public void whenContactsCsvFileCorrect_thenParseSucessful() throws Exception{
        Map<Long, List<Instant>> contactListings = csvReadFileService.getContactListingsMap();
        Assertions.assertEquals(300, contactListings.size());
        //Assertions.assertEquals(14095, listings.size());
    }

}
