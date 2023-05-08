package uz.ms.weatherservice.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.WeatherDto;

public interface WeatherService {

    /**
     * Postman: http://localhost:8080/weather/update-city-weather
     * Body:
     * {
     *     "city":{"id":1},
     *     "temperature":26
     * }
     */
    Mono<WeatherDto> updateWeather(WeatherDto weather);

    /**
     * Postman: http://localhost:8080/weather/get-user-subscriptions-weather
     */
    Flux<WeatherDto> getSubscriptionsWeather();
}
