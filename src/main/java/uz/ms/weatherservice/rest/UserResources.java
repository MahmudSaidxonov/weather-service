package uz.ms.weatherservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.GetTokenDto;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.dto.UserDto;
import uz.ms.weatherservice.service.UserService;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserResources {

    private final UserService userService;

    @PostMapping("add-user")
    public Mono<ResponseDto<UserDto>> addUser(@RequestBody UserDto dto) {
        return userService.addUser(dto);
    }

    @PostMapping("get-token")
    public Mono<ResponseDto<String>> getToken(@RequestBody GetTokenDto getTokenDto){
        return userService.getToken(getTokenDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("get-all-user")
    public Flux<UserDto> getAllUsers(){
        return userService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PatchMapping("edit-user")
    public Mono<ResponseDto<UserDto>> editUser(@RequestBody UserDto userDto){
        return userService.editUser(userDto);
    }
}
