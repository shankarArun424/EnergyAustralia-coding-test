package com.example.energyaustraliacodingtest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JavaType;
import com.google.gson.*;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@SpringBootApplication
public class EnergyAustraliaCodingTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergyAustraliaCodingTestApplication.class, args);
	}

	@RestController
	public class MusicFestivalBands {
		Logger logger = LoggerFactory.getLogger(MusicFestivalBands.class);
		@GetMapping("/api/v1/festivals")
		public ResponseEntity<String> festivalsList() {
			try {
				logger.info("Getting the API for the listing of music festival");

				Map<String, List<Map<String, Object>>> recordLabelsMap = new HashMap<>();

				/* API connecting part */
//				logger.info("Loading API url");
//				String festivalApiURL = "https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals";
//				RestTemplate restApi = new RestTemplate();
//				String festivalDataResult = restApi.getForObject(festivalApiURL, String.class);
//				logger.info("API data retrieved in 200/429");

				/* Local data part for testing */
				String festivalDataResult = "[{\"name\":\"Trainerella\",\"bands\":[{\"name\":\"WildAntelope\",\"recordLabel\":\"StillBottomRecords\"},{\"name\":\"ManishDitch\",\"recordLabel\":\"ACR\"},{\"name\":\"AdrianVenti\",\"recordLabel\":\"MonocracyRecords\"},{\"name\":\"YOUKRANE\",\"recordLabel\":\"AntiRecords\"}]},{\"name\":\"LOL-palooza\",\"bands\":[{\"name\":\"WinterPrimates\",\"recordLabel\":\"\"},{\"name\":\"FrankJupiter\",\"recordLabel\":\"PacificRecords\"},{\"name\":\"JillBlack\",\"recordLabel\":\"FourthWomanRecords\"},{\"name\":\"WerewolfWeekday\",\"recordLabel\":\"XSRecordings\"}]},{\"name\":\"TwistedTour\",\"bands\":[{\"name\":\"Summon\",\"recordLabel\":\"Outerscope\"},{\"name\":\"Auditones\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"Squint-281\"}]},{\"bands\":[{\"name\":\"CritterGirls\",\"recordLabel\":\"ACR\"},{\"name\":\"Propeller\",\"recordLabel\":\"PacificRecords\"}]},{\"name\":\"SmallNightIn\",\"bands\":[{\"name\":\"YankeEast\",\"recordLabel\":\"MEDIOCREMusic\"},{\"name\":\"WildAntelope\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"Squint-281\",\"recordLabel\":\"Outerscope\"},{\"name\":\"GreenMildColdCapsicum\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"TheBlackDashes\",\"recordLabel\":\"FourthWomanRecords\"}]}]";
				logger.info("String data hot coded: " + festivalDataResult);

				Gson gsonObject = new Gson();
				JsonArray jsonArray = gsonObject.fromJson(festivalDataResult, JsonArray.class); // for array of object

				List<Map<String, Object>> bandsList = recordLabelsMap.getOrDefault("", new ArrayList<>());

				for (JsonElement element: jsonArray) {
					logger.info("Single element list: " + element);
					if(element.isJsonObject()){
						JsonObject jsonObject = element.getAsJsonObject();
						String festivalName = "";
						if (jsonObject.get("name") != null) {
							logger.info("Festival name: " + jsonObject.get("name"));
							if (jsonObject.get("name") != null) {
								festivalName = jsonObject.get("name").toString();
								logger.info("name: " + festivalName);
							}
						}

						if (jsonObject.get("bands") != null) {
							JsonElement bands = jsonObject.get("bands");
							logger.info("Bands: " + bands);
							if (bands != null && bands.isJsonArray()) {
								JsonArray bandArray = bands.getAsJsonArray();
								// Band array looping statement
								for (JsonElement bandElement : bandArray) {
									JsonObject bandObject = bandElement.getAsJsonObject();
									Map<String, Object> bandMap = new HashMap<>();
									logger.info("bandObject: " + bandObject);
									String name = " "; // band name
									if(bandObject.get("name") != null){
										name = bandObject.get("name").toString();
									}
									String recordLabel = " "; // Music industry name.
									if(bandObject.get("recordLabel") != null) {
										recordLabel = bandObject.get("recordLabel").toString();

									}

									bandMap.put("MusicCompany", recordLabel);
									bandMap.put("name", name);
									bandMap.put("festivalName", festivalName);

									bandsList.add(bandMap);
									recordLabelsMap.put(recordLabel, bandsList);
									logger.info("recordLabelsMap: " + recordLabelsMap);
								}
							}
						}

					}
				}

				logger.info("Sorting of data for music industry names");
//				ObjectMapper objectMapper = new ObjectMapper();
//				Map<String, Object> sortedList = objectMapper.readValue(bandsList.toString(), new TypeReference<Map<String, Object>>() {});
//				Collections.sort(sortedList, (b1, b2) -> b1.get("MusicCompany").toString().compareTo(b2.get("MusicCompany").toString()));
				// Add into the controller
				RecordLabelCompany rlc = new RecordLabelCompany();
				JsonArray totalData = new JsonArray();

				logger.info("bandsList: " + bandsList);

				for (Map<String, Object> completeElement : bandsList) {
					List<Band> bandGroupList = new ArrayList<>();
					for (Map<String, Object> completeElement2 : bandsList) {
						List<Festival> festivalGroupList = new ArrayList<>();
						logger.info("Band names: "  + completeElement2.get("MusicCompany") + " = " + completeElement.get("MusicCompany") + ", " + completeElement2.get("name"));
						if (completeElement.get("MusicCompany").equals(completeElement2.get("MusicCompany"))) {
							for (Map<String, Object> completeElement3 : bandsList) {
								if (completeElement.get("MusicCompany").equals(completeElement3.get("MusicCompany")) &&
										completeElement2.get("name").equals(completeElement3.get("name"))) {
									Festival tempFestival = new Festival();
									tempFestival.setName((String) completeElement3.get("festivalName"));
									festivalGroupList.add(tempFestival);

								}
							}
							ObjectMapper mapper = new ObjectMapper();
							String json = mapper.writeValueAsString(festivalGroupList);
							System.out.println("festivalGroupList: " + json);
							Band tempBand = new Band();
							tempBand.setName(completeElement2.get("name").toString());
							tempBand.setFestivals(festivalGroupList);
							bandGroupList.add(tempBand);
							logger.info("Band name: " + completeElement2.get("name"));
							ObjectMapper mapper2 = new ObjectMapper();
							String json2 = mapper2.writeValueAsString(bandGroupList);
							System.out.println("bandGroupList: " + json2);
						}
					}
					rlc.setLabel((String) completeElement.get("MusicCompany"));
					rlc.setBands(bandGroupList);
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(rlc);
					System.out.println("rlc: " + json);

					JsonObject finalJsonArray = gsonObject.fromJson(json, JsonObject.class);

					totalData.add(finalJsonArray);
				}

				System.out.println("totalData: " + totalData);

//				JsonObject jsonObject = new JsonObject();
//				jsonObject.add("Result", totalData);

				JsonElement completeDataElement = totalData;
				String jsonString = gsonObject.toJson(completeDataElement);
//				JsonArray jsonArray2 = gsonObject.fromJson(festivalDataResult, JsonArray.class); // for array of object

				return ResponseEntity.status(HttpStatus.OK).body(jsonString);

			} catch(Exception e) {
				logger.error("There's been an issue at server side: " + e);
				if(e.getMessage() != null && e.getMessage().equals( "429 Too Many Requests: \"Too many requests, throttling\"")){
					return ResponseEntity.ok().body(null);
				}
			}
			return ResponseEntity.ok().body(null);
		}
	}

	public class Festival {
		private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

	public class Band {
		private String name;
		private List<Festival> festivals;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Festival> getFestivals() {
			return festivals;
		}
		public void setFestivals(List<Festival> festivals) {
			this.festivals = festivals;
		}
	}

	public class RecordLabelCompany {
		private String label;
		private List<Band> bands;
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public List<Band> getBands() {
			return bands;
		}
		public void setBands(List<Band> bands) {
			this.bands = bands;
		}
		@JsonProperty("MusicCompany")
		public String getMusicCompany() {
			return label;
		}
	}
}
