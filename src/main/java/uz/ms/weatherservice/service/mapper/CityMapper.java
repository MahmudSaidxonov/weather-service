package uz.ms.weatherservice.service.mapper;

import org.mapstruct.Mapper;
import uz.ms.weatherservice.dto.CityDto;
import uz.ms.weatherservice.model.City;

@Mapper(componentModel = "spring")
public interface CityMapper extends CommonMapper<CityDto, City> {
}
