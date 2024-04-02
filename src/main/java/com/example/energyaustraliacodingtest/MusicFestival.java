package com.example.energyaustraliacodingtest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import models.Band;
import models.Festival;
import models.RecordLabelCompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

// https://eacp.energyaustralia.com.au/codingtest/api-docs/#/festivals/APIFestivalsGet
// The code below makes a GET request to the API endpoint at 'apiUrl'
// (https://eacp.energyaustralia.com.au/codingtest/api/v1/musiclabel),
// expecting a list of Festival objects in return. The response body is extracted and stored in the 'festivals' variable.

@RestController
public class MusicFestival {
        Logger logger = LoggerFactory.getLogger(MusicFestival.class);

        @Value("${festivals.api.url}")
        String festivalApiURL;

        @GetMapping("/api/v1/musiclabel")
        public Object festivalsList() {
            try {
                logger.info("Getting the API data for the listing of music festival");
                Map<String, List<Map<String, Object>>> recordLabelsMap = new HashMap<>();

                /* API connecting part */
                logger.info("API URL: " + festivalApiURL);
				logger.info("Getting data from API url");
				RestTemplate restApi = new RestTemplate();
				String festivalDataResult = restApi.getForObject(festivalApiURL, String.class);
				logger.info("API data retrieved in 200/429");

                /* Local data part for testing */
//                String festivalDataResult = "[{\"name\":\"Trainerella\",\"bands\":[{\"name\":\"WildAntelope\",\"recordLabel\":\"StillBottomRecords\"},{\"name\":\"ManishDitch\",\"recordLabel\":\"ACR\"},{\"name\":\"AdrianVenti\",\"recordLabel\":\"MonocracyRecords\"},{\"name\":\"YOUKRANE\",\"recordLabel\":\"AntiRecords\"}]},{\"name\":\"LOL-palooza\",\"bands\":[{\"name\":\"WinterPrimates\",\"recordLabel\":\"\"},{\"name\":\"FrankJupiter\",\"recordLabel\":\"PacificRecords\"},{\"name\":\"JillBlack\",\"recordLabel\":\"FourthWomanRecords\"},{\"name\":\"WerewolfWeekday\",\"recordLabel\":\"XSRecordings\"}]},{\"name\":\"TwistedTour\",\"bands\":[{\"name\":\"Summon\",\"recordLabel\":\"Outerscope\"},{\"name\":\"Auditones\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"Squint-281\"}]},{\"bands\":[{\"name\":\"CritterGirls\",\"recordLabel\":\"ACR\"},{\"name\":\"Propeller\",\"recordLabel\":\"PacificRecords\"}]},{\"name\":\"SmallNightIn\",\"bands\":[{\"name\":\"YankeEast\",\"recordLabel\":\"MEDIOCREMusic\"},{\"name\":\"WildAntelope\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"Squint-281\",\"recordLabel\":\"Outerscope\"},{\"name\":\"GreenMildColdCapsicum\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"TheBlackDashes\",\"recordLabel\":\"FourthWomanRecords\"}]}]";
//                logger.debug("String data hot coded: " + festivalDataResult);

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
                            logger.debug("Bands: " + bands);
                            if (bands != null && bands.isJsonArray()) {
                                JsonArray bandArray = bands.getAsJsonArray();
                                // Band array looping statement
                                for (JsonElement bandElement : bandArray) {
                                    JsonObject bandObject = bandElement.getAsJsonObject();
                                    Map<String, Object> bandMap = new HashMap<>();
                                    logger.debug("bandObject: " + bandObject);
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

                logger.debug("Bands List: " + bandsList);
                List<String> musicIndustryNames = new ArrayList<String>();

                for (Map<String, Object> completeElement : bandsList) {
                    // remove if the music name comes again.
                    if(musicIndustryNames.size() == 0 || !musicIndustryNames.contains(completeElement.get("MusicCompany").toString())){
                        musicIndustryNames.add(completeElement.get("MusicCompany").toString());
                    } else if(musicIndustryNames.contains(completeElement.get("MusicCompany").toString())) {
                        logger.debug("Found: " + completeElement.get("MusicCompany").toString());
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
                            logger.debug("Sorting of festival data for music industry names");
                            Collections.sort(festivalGroupList, (b1, b2) -> b1.getName().toString().compareTo(b2.getName().toString()));
                            Band tempBand = new Band();
                            tempBand.setName(completeElement2.get("name").toString());
                            tempBand.setFestivals(festivalGroupList);
                            bandGroupList.add(tempBand);
                        }
                    }
                    // Sorting band group names
                    logger.debug("Sorting of band data for music industry names");
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

    // For sorting the RecordLabelCompany using Comparator library
    public class SortingCustomObjectComparator implements Comparator<RecordLabelCompany> {
        @Override
        public int compare(RecordLabelCompany obj1, RecordLabelCompany obj2) {
            return obj1.getLabel().compareTo(obj2.getLabel());
        }
    }
}
