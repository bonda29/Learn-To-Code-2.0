package com.example.learntocode.payload.weather;

import com.example.learntocode.payload.weather.data.Current;
import com.example.learntocode.payload.weather.data.Forecast;
import com.example.learntocode.payload.weather.data.Location;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeatherForecast {
    private Location location;
    private Current current;
    private Forecast forecast;
}
