package com.bharath.tasks.autoscout24.controller;

import com.bharath.tasks.autoscout24.dto.AvgListingPriceDto;
import com.bharath.tasks.autoscout24.dto.ListingsPerMonthDto;
import com.bharath.tasks.autoscout24.dto.MakeDistributionDto;
import com.bharath.tasks.autoscout24.dto.PriceDto;
import com.bharath.tasks.autoscout24.model.Listing;
import com.bharath.tasks.autoscout24.service.CsvReadFileService;
import com.bharath.tasks.autoscout24.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reports")
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {

    CsvReadFileService csvReadFileService;

    ReportServiceImpl reportService;

    @Autowired
    ReportController(final CsvReadFileService csvReadFileService, ReportServiceImpl reportServiceImpl) {
        this.csvReadFileService = csvReadFileService;
        this.reportService = reportServiceImpl;
    }

    @GetMapping("AvgListingPrice")
    public List<AvgListingPriceDto> getAverageListingPriceReport() {
        List<Listing> listings = csvReadFileService.getListingsList();
        return reportService.getAvgListingPrice(listings);
    }

    @GetMapping("MakeDistributionPercent")
    public List<MakeDistributionDto> getMakeDistributionPercent() {
        List<Listing> listings = csvReadFileService.getListingsList();
        return reportService.getMakeDistributionPercent(listings);
    }

    @GetMapping("Avg30PercentMostContactedListingsPrice")
    public PriceDto getAvg30PercentMostContactedListingsPrice() {
        Map<Long, Listing> listingsMap = csvReadFileService.getListingIdMap();
        Map<Long, List<Instant>> contactListings = csvReadFileService.getContactListingsMap();
        return reportService.getAvg30PercentMostContactedListingsPrice(listingsMap, contactListings);
    }

    @GetMapping("Top5ListingsPerMonth")
    public List<ListingsPerMonthDto> getTop5ListingsPerMonth() {
        Map<Long, Listing> listingsMap = csvReadFileService.getListingIdMap();
        Map<Long, List<Instant>> contactListings = csvReadFileService.getContactListingsMap();
        return reportService.getTop5ListingsPerMonth(listingsMap, contactListings);

    }

    @PostMapping(path = "UploadListings", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadListings(@RequestParam("File") MultipartFile file) throws IOException {
        csvReadFileService.updateListingsList(file);
        return "Listing File uploaded successfully";
    }

    @PostMapping(path = "UploadContactListings", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadContactListings(@RequestParam("File") MultipartFile file) throws IOException {
        csvReadFileService.updateContactListingsList(file);
        return "Contacts File uploaded successfully";
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        ex.printStackTrace();
        if (ex instanceof IllegalStateException)
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
