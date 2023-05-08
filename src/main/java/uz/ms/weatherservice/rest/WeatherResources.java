package uz.ms.weatherservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.WeatherDto;
import uz.ms.weatherservice.service.WeatherService;

@RestController
@RequestMapping("weather")
@RequiredArgsConstructor
public class WeatherResources {
    private final WeatherService weatherService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("update-city-weather")
    public Mono<WeatherDto> updateCityWeather(@RequestBody WeatherDto dto){
        return weatherService.updateWeather(dto);
    }

    @GetMapping("get-user-subscriptions-weather")
    public Flux<WeatherDto> getSubscriptionsWeather(){
        return weatherService.getSubscriptionsWeather();
    }
}
