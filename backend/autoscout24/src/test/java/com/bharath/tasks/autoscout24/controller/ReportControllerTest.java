package com.bharath.tasks.autoscout24.controller;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {


    private MockMvc mockMvc;

    @Autowired
    public ReportControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetAverageListingPriceReport() throws Exception {
        String expectedResult = "[\n" +
                "  {\n" +
                "    \"sellerType\": \"private\",\n" +
                "    \"averagePrice\": \"€ 26.081,-\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"sellerType\": \"other\",\n" +
                "    \"averagePrice\": \"€ 25.318,-\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"sellerType\": \"dealer\",\n" +
                "    \"averagePrice\": \"€ 25.038,-\"\n" +
                "  }\n" +
                "]";
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/AvgListingPrice"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));

    }

    @Test
    public void testGetMakeDistributionPercent() throws Exception {
        String exptectedResult = "[\n" +
                "  {\n" +
                "    \"make\": \"Mercedes-Benz\",\n" +
                "    \"distributionPercent\": \"16%\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"make\": \"Toyota\",\n" +
                "    \"distributionPercent\": \"16%\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"make\": \"Audi\",\n" +
                "    \"distributionPercent\": \"14%\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"make\": \"Renault\",\n" +
                "    \"distributionPercent\": \"14%\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"make\": \"Mazda\",\n" +
                "    \"distributionPercent\": \"13%\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"make\": \"VW\",\n" +
                "    \"distributionPercent\": \"10%\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"make\": \"Fiat\",\n" +
                "    \"distributionPercent\": \"9%\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"make\": \"BWM\",\n" +
                "    \"distributionPercent\": \"7%\"\n" +
                "  }\n" +
                "]";

        mockMvc.perform(MockMvcRequestBuilders.get("/reports/MakeDistributionPercent"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(exptectedResult));
    }

    @Test
    public void testGetAvg30PercentMostContactedListingsPrice() throws Exception {
        String expectedResult = "{\n" +
                "  \"price\": \"€ 24.908,-\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/Avg30PercentMostContactedListingsPrice"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResult));
    }

    @Test
    public void testGetTop5ListingsPerMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reports/Top5ListingsPerMonth"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].listings[0].totalAmountOfContacts", Matchers.is(21)));
    }

    @Test
    public void testUploadListings() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "File",
                "test.csv",
                MediaType.TEXT_PLAIN_VALUE,
                Files.readAllBytes(Paths.get("resources/csv/listings/listings.csv"))
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/reports/UploadListings").file(file))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Listing File uploaded successfully"));
    }

    @Test
    public void testUploadContactListings() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "File",
                "test.csv",
                MediaType.TEXT_PLAIN_VALUE,
                Files.readAllBytes(Paths.get("resources/csv/contacts/contacts.csv"))
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/reports/UploadContactListings").file(file))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Contacts File uploaded successfully"));
    }

    @Test
    public void whenUploadContactListingsIncorrectFileExtension_then422StatusCode() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "File",
                "test.ctx",
                MediaType.TEXT_PLAIN_VALUE,
                Files.readAllBytes(Paths.get("resources/csv/contacts/contacts.csv"))
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/reports/UploadContactListings").file(file))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void whenUploadListingsIncorrectFileExtension_then422StatusCode() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "File",
                "test.ctx",
                MediaType.TEXT_PLAIN_VALUE,
                "".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/reports/UploadListings").file(file))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
}
