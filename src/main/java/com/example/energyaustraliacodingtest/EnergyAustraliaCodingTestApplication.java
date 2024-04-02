package com.example.energyaustraliacodingtest;

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
		@GetMapping("/api/v1/musiclabel")
		public Object festivalsList() {
			try {
				logger.info("Getting the API for the listing of music festival");
				Map<String, List<Map<String, Object>>> recordLabelsMap = new HashMap<>();

				/* API connecting part */
				logger.info("Getting data from API url");
				String festivalApiURL = "https://eacp.energyaustralia.com.au/codingtest/api/v1/festivals";
				RestTemplate restApi = new RestTemplate();
				String festivalDataResult = restApi.getForObject(festivalApiURL, String.class);
				logger.info("API data retrieved in 200/429");

				/* Local data part for testing */
				// String festivalDataResult = "[{\"name\":\"Trainerella\",\"bands\":[{\"name\":\"WildAntelope\",\"recordLabel\":\"StillBottomRecords\"},{\"name\":\"ManishDitch\",\"recordLabel\":\"ACR\"},{\"name\":\"AdrianVenti\",\"recordLabel\":\"MonocracyRecords\"},{\"name\":\"YOUKRANE\",\"recordLabel\":\"AntiRecords\"}]},{\"name\":\"LOL-palooza\",\"bands\":[{\"name\":\"WinterPrimates\",\"recordLabel\":\"\"},{\"name\":\"FrankJupiter\",\"recordLabel\":\"PacificRecords\"},{\"name\":\"JillBlack\",\"recordLabel\":\"FourthWomanRecords\"},{\"name\":\"WerewolfWeekday\",\"recordLabel\":\"XSRecordings\"}]},{\"name\":\"TwistedTour\",\"bands\":[{\"name\":\"Summon\",\"recordLabel\":\"Outerscope\"},{\"name\":\"Auditones\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"Squint-281\"}]},{\"bands\":[{\"name\":\"CritterGirls\",\"recordLabel\":\"ACR\"},{\"name\":\"Propeller\",\"recordLabel\":\"PacificRecords\"}]},{\"name\":\"SmallNightIn\",\"bands\":[{\"name\":\"YankeEast\",\"recordLabel\":\"MEDIOCREMusic\"},{\"name\":\"WildAntelope\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"Squint-281\",\"recordLabel\":\"Outerscope\"},{\"name\":\"GreenMildColdCapsicum\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"TheBlackDashes\",\"recordLabel\":\"FourthWomanRecords\"}]}]";
				// logger.info("String data hot coded: " + festivalDataResult);

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
									logger.info("Record Label Mapping: " + recordLabelsMap);
								}
							}
						}

					}
				}

				List<RecordLabelCompany> recordLabelCompanyList = new ArrayList<>();
				// Add into the controller
				RecordLabelCompany rlc = new RecordLabelCompany();
				JsonArray totalData = new JsonArray();

				logger.info("Bands List: " + bandsList);
				List<String> musicIndustryNames = new ArrayList<String>();

				for (Map<String, Object> completeElement : bandsList) {
					// remove if the music name comes again.
					if(musicIndustryNames.size() == 0 || !musicIndustryNames.contains(completeElement.get("MusicCompany").toString())){
						musicIndustryNames.add(completeElement.get("MusicCompany").toString());
					} else if(musicIndustryNames.contains(completeElement.get("MusicCompany").toString())) {
						logger.info("Found: " + completeElement.get("MusicCompany").toString());
						continue;
					}
					List<Band> bandGroupList = new ArrayList<>();
					for (Map<String, Object> completeElement2 : bandsList) {
						List<Festival> festivalGroupList = new ArrayList<>();
						if (completeElement.get("MusicCompany").equals(completeElement2.get("MusicCompany"))) {
							for (Map<String, Object> completeElement3 : bandsList) {
								if (completeElement.get("MusicCompany").equals(completeElement3.get("MusicCompany")) &&
										completeElement2.get("name").equals(completeElement3.get("name"))) {
									Festival tempFestival = new Festival();
									tempFestival.setName((String) completeElement3.get("festivalName"));
									festivalGroupList.add(tempFestival);
								}
							}
							// sorting festival names
							logger.info("Sorting of festival data for music industry names");
							Collections.sort(festivalGroupList, (b1, b2) -> b1.getName().toString().compareTo(b2.getName().toString()));
							Band tempBand = new Band();
							tempBand.setName(completeElement2.get("name").toString());
							tempBand.setFestivals(festivalGroupList);
							bandGroupList.add(tempBand);
						}
					}
					// Sorting band group names
					logger.info("Sorting of band data for music industry names");
					Collections.sort(bandGroupList, (b1, b2) -> b1.getName().toString().compareTo(b2.getName().toString()));

					rlc.setLabel((String) completeElement.get("MusicCompany"));
					rlc.setBands(bandGroupList);

					Map<String, Object> recordLabelCompanyHashMap = new HashMap<>();
					recordLabelCompanyHashMap.put("Bands", bandGroupList);
					recordLabelCompanyHashMap.put("label", (String) completeElement.get("MusicCompany"));

					// recordLabelCompanyList.add(recordLabelCompanyHashMap); // Old approach

					recordLabelCompanyList.add(new RecordLabelCompany((String) completeElement.get("MusicCompany"), bandGroupList));

					String json = new Gson().toJson(rlc);
					JsonObject finalJsonArray = gsonObject.fromJson(json, JsonObject.class);
					totalData.add(finalJsonArray);
				}
				logger.info("Sorting of final data for music industry names");
				Collections.sort(recordLabelCompanyList, new SortingCustomObjectComparator());

				return ResponseEntity.status(HttpStatus.OK).body(recordLabelCompanyList);

			} catch(Exception e) {
				logger.error("There's been an issue at server side: " + e);
				if(e.getMessage() != null && e.getMessage().equals( "429 Too Many Requests: \"Too many requests, throttling\"")){
					return ResponseEntity.status(429).body(null);
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
		public RecordLabelCompany(){}
		public RecordLabelCompany(String label, List<Band> bands){
			this.label = label;
			this.bands = bands;
		}
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
	}

	public class SortingCustomObjectComparator implements Comparator<RecordLabelCompany> {
		@Override
		public int compare(RecordLabelCompany obj1, RecordLabelCompany obj2) {
			return obj1.getLabel().compareTo(obj2.getLabel());
		}
	}

	public class RecordLabelCompanyList {
		private List<RecordLabelCompany> recordLableList;
		public RecordLabelCompanyList() {
			recordLableList = new ArrayList<>();
		}
		public void setRecordLabelCompanyList(List<RecordLabelCompany> recordLableList) {
			this.recordLableList = recordLableList;
		}
	}
}
