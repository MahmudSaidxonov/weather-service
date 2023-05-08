package uz.ms.weatherservice.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
    private Integer id;
    private CityDto city;
    private Integer temperature;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDateTime date;
}
