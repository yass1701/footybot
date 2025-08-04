package com.footybot.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExternalFootballApiService {

    @Value("${football.api.key}")
    private String apiToken;
    
    private static final String BASE_URL = "https://api.football-data.org/v4";
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * MAIN METHOD FOR YOUR COLLEGE PROJECT
     * Gets ALL Premier League data in one comprehensive response
     */
    public String getAllPremierLeagueData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        
        // Get all Premier League teams with their squads
        String url = BASE_URL + "/competitions/PL/teams";
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Auth-Token", apiToken)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + response.statusCode() + 
                                     ", body: " + response.body());
        }

        return response.body();
    }

    /**
     * ENHANCED VERSION - Gets teams + current standings + recent matches
     * Perfect for comprehensive college project
     */
    public String getCompleteFootballData() throws IOException, InterruptedException {
        try {
            // Get individual data
            String teams = fetchPremierLeagueTeams();
            String standings = fetchPremierLeagueStandings();
            String matches = fetchRecentMatches();
            
            // Parse JSON strings to JsonNode
            JsonNode teamsNode = objectMapper.readTree(teams);
            JsonNode standingsNode = objectMapper.readTree(standings);
            JsonNode matchesNode = objectMapper.readTree(matches);
            
            // Create combined response using ObjectMapper
            StringBuilder combinedData = new StringBuilder();
            combinedData.append("{");
            combinedData.append("\"teams\":").append(teams).append(",");
            combinedData.append("\"standings\":").append(standings).append(",");
            combinedData.append("\"recentMatches\":").append(matches);
            combinedData.append("}");
            
            return combinedData.toString();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch complete football data: " + e.getMessage());
        }
    }

    // Helper methods for individual data types
    public String fetchPremierLeagueTeams() throws IOException, InterruptedException {
        return makeApiCall("/competitions/PL/teams");
    }
    
    public String fetchPremierLeagueStandings() throws IOException, InterruptedException {
        return makeApiCall("/competitions/PL/standings");
    }
    
    public String fetchRecentMatches() throws IOException, InterruptedException {
        return makeApiCall("/competitions/PL/matches?status=FINISHED&limit=10");
    }
    
    public String fetchUpcomingMatches() throws IOException, InterruptedException {
        return makeApiCall("/competitions/PL/matches?status=SCHEDULED&limit=10");
    }

    // Generic API call method
    private String makeApiCall(String endpoint) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        
        String url = BASE_URL + endpoint;
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-Auth-Token", apiToken)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("API call failed with status: " + response.statusCode());
        }

        return response.body();
    }
}