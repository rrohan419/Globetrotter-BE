package com.rohan.globetrotter.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AIResponseModel {
    
    private String city;

    private String country;

    private List<String> clues;

    @JsonProperty()
    private List<String> fun_fact;

    private List<String> trivia;
}
