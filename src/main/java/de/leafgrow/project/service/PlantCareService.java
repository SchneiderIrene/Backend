package de.leafgrow.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//  Интеграция с внешними API
@Service
public class PlantCareService {

    private final RestTemplate restTemplate;

    @Autowired
    public PlantCareService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCareInstructions(String plantName) {
        String apiUrl = "https://plant-care-api.example.com/instructions?plantName=" + plantName;
        return restTemplate.getForObject(apiUrl, String.class);
    }

    public String analyzePlantData(String plantName) {
        String apiUrl = "https://plant-data-analysis-api.example.com/analyze?plantName=" + plantName;
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
