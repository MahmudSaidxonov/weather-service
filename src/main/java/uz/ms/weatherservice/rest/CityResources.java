package uz.ms.weatherservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.CityDto;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.service.CityService;

@RestController
@RequestMapping("city")
@RequiredArgsConstructor
public class CityResources {

    private final CityService cityService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("add-city")
    public Mono<ResponseDto<CityDto>> addCity(@RequestBody CityDto dto){
        return cityService.addCity(dto);
    }

    @GetMapping("cities-list")
    public Flux<CityDto> getCitiesList(){
        return cityService.getCitiesList();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("edit-city")
    public Mono<ResponseDto<CityDto>> editCity(@RequestBody CityDto dto){
        return cityService.editCity(dto);
    }

}
