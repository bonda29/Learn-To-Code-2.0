package com.example.learntocode.payload.weather.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Current {
    private String time;
    private String temp_c;
    private String feelslike_c;
    private Condition condition;
    private String wind_kph;
    private String wind_dir;
    private String is_day;
    private String last_updated;
    private String last_updated_epoch;
}
