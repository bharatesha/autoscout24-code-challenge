package com.bharath.tasks.autoscout24.service;

import com.bharath.tasks.autoscout24.model.Listing;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Scope("singleton")
public class CsvReadFileService {

    private static final String CSV_DELIMETER_PATTERN = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    private static final String CSV_LISTING_FILE_PATH = "resources/csv/listings/listings.csv";
    private static final String CSV_CONTACT_FILE_PATH = "resources/csv/contacts/contacts.csv";

    private static List<Listing> listingsList = null;
    private static Map<Long, List<Instant>> contactListingsMap = null;
    private static Map<Long, Listing> listingIdMap = null;

    CsvReadFileService() {
        try {
            setListings(parseCsvFileListings(CSV_LISTING_FILE_PATH));
            contactListingsMap = parseCsvFileContacts(CSV_CONTACT_FILE_PATH);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Map<Long, List<Instant>> getContactListingsMap() {
        return contactListingsMap;
    }

    public List<Listing> getListingsList() {
        return listingsList;
    }

    public Map<Long, Listing> getListingIdMap() {
        return listingIdMap;
    }

    public void updateListingsList(MultipartFile file) throws IOException {
        if (isInValidCsvFile(file)) {
            handleInvalidCSVFile();
        }
        setListings(parseCsvFileListings(file));
    }

    public void updateContactListingsList(MultipartFile file) throws IOException {
        if (isInValidCsvFile(file)) {
            handleInvalidCSVFile();
        }
        contactListingsMap = parseCsvFileContacts(file);
    }

    private void setListings(List<Listing> listings) {
        listingsList = listings;
        listingIdMap = listingsList.stream()
                .collect(Collectors.toMap(Listing::getId, Function.identity()));
    }

    private List<Listing> parseCsvFileListings(final String filePath) throws IOException {

        List<Listing> listingsList;
        try (Stream<String> listStream = Files.lines(Paths.get(filePath)).skip(1)) {
            listingsList = listStream.map(line -> Arrays.asList(line.split(CSV_DELIMETER_PATTERN)))
                    .map(this::convertToListing)
                    .collect(Collectors.toList());
        }
        return listingsList;
    }

    private List<Listing> parseCsvFileListings(MultipartFile file) throws IOException {

        List<Listing> listingsList;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            listingsList = br.lines().skip(1)
                    .map(line -> Arrays.asList(line.split(CSV_DELIMETER_PATTERN)))
                    .map(this::convertToListing)
                    .collect(Collectors.toList());
        }
        return listingsList;
    }

    private Map<Long, List<Instant>> parseCsvFileContacts(final String filePath) throws IOException {

        Map<Long, List<Instant>> contactListingsMapHolder = new HashMap<>();

        try (Stream<String> listStream = Files.lines(Paths.get(filePath)).skip(1)) {
            listStream.map(line -> Arrays.asList(line.split(CSV_DELIMETER_PATTERN)))
                    .forEach(list -> addToContactMap(list, contactListingsMapHolder));
        }
        return contactListingsMapHolder;

    }

    private Map<Long, List<Instant>> parseCsvFileContacts(final MultipartFile file) throws IOException {

        Map<Long, List<Instant>> contactListingsMapHolder = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader((file.getInputStream())))) {
            br.lines().skip(1)
                    .map(line -> Arrays.asList(line.split(CSV_DELIMETER_PATTERN)))
                    .forEach(list -> addToContactMap(list, contactListingsMapHolder));
        }
        return contactListingsMapHolder;
    }

    private Listing convertToListing(final List<String> listingsList) {

        if (listingsList.size() != 5)
            handleInvalidCSVFile();

        final long id = Long.parseLong(listingsList.get(0));
        final String make = listingsList.get(1).replace("\"", "");
        final BigDecimal price = new BigDecimal(listingsList.get(2));
        final Integer mileage = Integer.valueOf(listingsList.get(3));
        final String sellerType = listingsList.get(4).replace("\"", "");
        return new Listing(id, make, price, mileage, sellerType);
    }

    private void addToContactMap(final List<String> contactsList, Map<Long, List<Instant>> contactListingsMapHolder) {

        if (contactsList.size() != 2)
            handleInvalidCSVFile();

        long id = Long.parseLong(contactsList.get(0));
        contactListingsMapHolder.computeIfAbsent(id, key -> new ArrayList<>());
        Instant contactDate = Instant.ofEpochMilli(Long.parseLong(contactsList.get(1)));
        contactListingsMapHolder.get(id).add(contactDate);
    }

    private boolean isInValidCsvFile(MultipartFile file) {
        //Check file extension is valid
        return !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv");
    }

    private void handleInvalidCSVFile() {
        throw new IllegalStateException("Invalid CSV File");
    }
}
