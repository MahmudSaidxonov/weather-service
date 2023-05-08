package uz.ms.weatherservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private Integer id;
    private CityDto city;
}
