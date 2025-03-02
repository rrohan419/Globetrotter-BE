package com.rohan.globetrotter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.globetrotter.model.AIResponseModel;
import com.rohan.globetrotter.service.ApiService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ApiController {
    
    private final ApiService apiService;

    @GetMapping("/destination/random")
    public ResponseEntity<List<AIResponseModel>> getResponse() {
        return ResponseEntity.ok(apiService.getRandomDestinations());
    }

}
