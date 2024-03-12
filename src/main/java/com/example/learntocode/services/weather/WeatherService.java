package com.example.learntocode.services.weather;

import com.example.learntocode.payload.weather.WeatherCurrent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherApi weatherApi;

    public ResponseEntity<WeatherCurrent> getCurrentWeather(String q) {
        return ResponseEntity.ok(weatherApi.getCurrentWeather(q));
    }

}
