package uz.ms.weatherservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityDto {
    private Integer id;
    private String cityName;
    private String country;
    private Boolean enabled;
}
