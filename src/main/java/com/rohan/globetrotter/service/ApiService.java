package com.rohan.globetrotter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohan.globetrotter.model.AIResponseModel;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ApiService {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
    private static final ObjectMapper mapper = new ObjectMapper();
    private final RestClient restClient;
    private final String apiKey;

    public ApiService(RestClient restClient) {
        this.restClient = restClient;
        this.apiKey = "AIzaSyBs7kOIkRJ9XpxpjWA8OsynelZvgfHedS0"; // Store API key securely
    }

    public List<AIResponseModel> getRandomDestinations() {
        try {
            String prompt = "Generate a list of 50 famous destinations in JSON format. Each destination should have a 'city', 'country', 'clues' (2 items), 'fun_fact' (2 items), and 'trivia' (2 items). Return as a JSON array.";

            String requestBody = """
                    {
                        "contents": [
                            {
                                "parts": [
                                    {
                                        "text": "%s"
                                    }
                                ]
                            }
                        ]
                    }
                    """.formatted(prompt);

            JsonNode response = restClient.post()
                    .uri(API_URL + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(JsonNode.class);

            // Extract and clean up response
            String generatedContent = response.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            // Remove markdown if present
            generatedContent = generatedContent.replaceAll("(?s)^```json\\s*|```$", "").trim();

            // Deserialize into a list of AIResponseModel
            return mapper.readValue(generatedContent, mapper.getTypeFactory().constructCollectionType(List.class, AIResponseModel.class));

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch random destinations", e);
        }
    }
}

