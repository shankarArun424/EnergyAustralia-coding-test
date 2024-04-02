package com.example.energyaustraliacodingtest;

import models.RecordLabelCompany;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicFestivalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MusicFestival musicFestival;

    @Test
    public void testFestivalsListEndpoint() throws Exception {
        List<RecordLabelCompany> mockResponse = new ArrayList<>();
        // Add some mock data
        RecordLabelCompany company1 = new RecordLabelCompany();
        company1.setLabel("Arun");
        RecordLabelCompany company2 = new RecordLabelCompany();
        company2.setLabel("Shankar");

        mockResponse.add(company1);
        mockResponse.add(company2);

        Mockito.when(musicFestival.festivalsList()).thenReturn(mockResponse); // mocking the api result with mock response.

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/musiclabel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].label").value("Arun"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].label").value("Shankar"));
    }

    @Test
    public void testFestivalsListEndpoint429() throws Exception {
        Mockito.when(musicFestival.festivalsList()).thenReturn(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/musiclabel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isTooManyRequests());
    }
}
