package uz.ms.weatherservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.ms.weatherservice.dto.CityDto;
import uz.ms.weatherservice.dto.SubscriptionDto;
import uz.ms.weatherservice.model.Subscription;

@Mapper(componentModel = "spring", imports = CityDto.class)
public interface SubscriptionMapper extends CommonMapper<SubscriptionDto, Subscription> {

    @Mapping(target = "city", expression = "java(CityDto.builder().id(entity.getCityId()).build())")
    SubscriptionDto toDto(Subscription entity);

}
