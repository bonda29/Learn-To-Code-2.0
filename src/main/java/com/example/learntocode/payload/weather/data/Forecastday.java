package com.example.learntocode.payload.weather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecastday {
    private String date;
    private String date_epoch;
    private Day day;
    private Astro astro;
    private List<Current> hour;

}
