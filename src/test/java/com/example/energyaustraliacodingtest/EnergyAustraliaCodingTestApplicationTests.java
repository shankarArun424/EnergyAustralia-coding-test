package com.example.energyaustraliacodingtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest
class EnergyAustraliaCodingTestApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private EnergyAustraliaCodingTestApplication EACTA = new EnergyAustraliaCodingTestApplication();

	@Test
	void contextLoads() {
//		EnergyAustraliaCodingTestApplication EACTA = new EnergyAustraliaCodingTestApplication();
	}

	@Test
	public void testMusicFestivalDataWithRecordLabels() {
		String baseUrl = "http://localhost:" + port;

        // You can make HTTP requests to your endpoints using TestRestTemplate
//		String response = EACTA.getForObject(baseUrl + "/api/v1/musiclabel", String.class);

	}

}
