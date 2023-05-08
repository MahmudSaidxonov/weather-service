package uz.ms.weatherservice.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.CityDto;
import uz.ms.weatherservice.dto.ResponseDto;

public interface CityService {

    /**
     * Postman: http://localhost:8080/city/add-city
     * Body:
     * {
     *     "cityName":"Samarqand",
     *     "country":"Uzbekistan"
     * }
     */
    Mono<ResponseDto<CityDto>> addCity(CityDto dto);

    /**
     * Postman: http://localhost:8080/city/cities-list
     */
    Flux<CityDto> getCitiesList();

    /**
     * Postman: http://localhost:8080/city/edit-city
     * Body:
     * {
     *     "id":1,
     *     "cityName":"Tashkent",
     *     "country":"Uzbekistan",
     *     "visibility":"true"
     * }
     */
    Mono<ResponseDto<CityDto>> editCity(CityDto dto);
}
