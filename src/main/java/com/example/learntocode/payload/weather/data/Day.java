package com.example.learntocode.payload.weather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {
    private String maxtemp_c;
    private String mintemp_c;
    private String avgtemp_c;
    private String maxwind_kph;
    private String totalprecip_mm;
    private Condition condition;
}
