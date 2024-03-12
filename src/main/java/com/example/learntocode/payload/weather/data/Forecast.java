package com.example.learntocode.payload.weather.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Forecast {
    @JsonProperty("forecastday")
    private List<Forecastday> forecastday;
}
