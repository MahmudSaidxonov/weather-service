package uz.ms.weatherservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.ms.weatherservice.dto.CityDto;
import uz.ms.weatherservice.dto.WeatherDto;
import uz.ms.weatherservice.model.Weather;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, CityDto.class})
public interface WeatherMapper extends CommonMapper<WeatherDto, Weather> {

    @Mapping(target = "date", expression = "java(LocalDateTime.now())")
    @Mapping(target = "cityId", expression = "java(dto.getCity().getId())")
    Weather toEntity(WeatherDto dto);

    @Mapping(target = "date", dateFormat = "dd.MM.yyyy HH:mm:ss")
    @Mapping(target = "city", expression = "java(CityDto.builder().id(entity.getCityId()).build())")
    WeatherDto toDto(Weather entity);
}
