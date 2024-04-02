package com.example.energyaustraliacodingtest;

import models.Band;
import models.Festival;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BandTest {

	@InjectMocks
	private Band band;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetName() {
		band.setName("Test Band");
		assertEquals("Test Band", band.getName());
	}

	@Test
	public void testGetFestivals() {
		List<Festival> festivals = new ArrayList<>();
		Festival f1 = new Festival();
		f1.setName("Test Festival 1");
		festivals.add(f1);
		band.setFestivals(festivals);

		assertEquals(1, band.getFestivals().size());
		assertEquals("Test Festival 1", band.getFestivals().get(0).getName());
	}
}
