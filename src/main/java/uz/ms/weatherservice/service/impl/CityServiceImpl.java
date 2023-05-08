package uz.ms.weatherservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import uz.ms.weatherservice.dto.CityDto;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.model.City;
import uz.ms.weatherservice.repository.CityRepository;
import uz.ms.weatherservice.service.CityService;
import uz.ms.weatherservice.service.mapper.CityMapper;

import static uz.ms.weatherservice.config.AppStatusCodes.*;


@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    @Override
    public Mono<ResponseDto<CityDto>> addCity(CityDto dto) {
        return cityRepository.findByCityName(dto.getCityName())
                .map(c ->
                        ResponseDto.<CityDto>builder()
                        .code(UNEXPECTED_ERROR_CODE)
                        .message("City with this name " + dto.getCityName() + " already exists")
                        .build())
                .switchIfEmpty(cityRepository.save(cityMapper.toEntity(dto))
                    .map(cty ->
                            ResponseDto.<CityDto>builder()
                            .data(cityMapper.toDto(cty))
                            .success(true)
                            .message("OK")
                            .build()));
    }

    @Override
    public Flux<CityDto> getCitiesList() {
        return cityRepository.findAllByEnabled(true)
                .map(cityMapper::toDto);
    }

    @Override
    public Mono<ResponseDto<CityDto>> editCity(CityDto dto) {
        Mono<City> city = cityRepository.findById(dto.getId())
                .map(c -> {
                    c.setCityName(dto.getCityName());
                    c.setCountry(dto.getCountry());
                    return c;
                });
        return city.flatMap(cityRepository::save)
                .map(c -> ResponseDto.<CityDto>builder()
                        .message("OK")
                        .success(true)
                        .data(cityMapper.toDto(c))
                        .build())
                .defaultIfEmpty(ResponseDto.<CityDto>builder()
                        .message("City with ID " + dto.getId() + " not found")
                        .code(NOT_FOUND_ERROR_CODE)
                        .data(dto)
                        .build());
    }
}
