package uz.ms.weatherservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.WeatherDto;
import uz.ms.weatherservice.model.User;
import uz.ms.weatherservice.repository.CityRepository;
import uz.ms.weatherservice.repository.WeatherRepository;
import uz.ms.weatherservice.service.WeatherService;
import uz.ms.weatherservice.service.mapper.CityMapper;
import uz.ms.weatherservice.service.mapper.WeatherMapper;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private Mono<WeatherDto> setCity(WeatherDto dto){
        return Mono.just(dto)
                .zipWith(cityRepository.findById(dto.getCity().getId()))
                .map(zip -> {
                    zip.getT1().setCity(cityMapper.toDto(zip.getT2()));
                    return zip.getT1();
                });
    }

    @Override
    public Mono<WeatherDto> updateWeather(WeatherDto weather) {
        return weatherRepository.save(weatherMapper.toEntity(weather))
                .map(weatherMapper::toDto)
                .flatMap(this::setCity);
    }

    @Override
    public Flux<WeatherDto> getSubscriptionsWeather() {
        return ReactiveSecurityContextHolder.getContext()
                .flux()
                .flatMap(auth -> {
                    User user = (User) auth.getAuthentication().getPrincipal();
                    return weatherRepository.getUserWeather(user.getId())
                            .map(weatherMapper::toDto)
                            .flatMap(this::setCity);
                });
    }
}
