package com.example.learntocode.controllers;

import com.example.learntocode.payload.weather.WeatherCurrent;
import com.example.learntocode.services.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity<WeatherCurrent> getCurrentWeather(@RequestParam String q) {
        return weatherService.getCurrentWeather(q);
    }
}
