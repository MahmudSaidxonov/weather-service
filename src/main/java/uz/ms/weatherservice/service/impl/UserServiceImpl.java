package uz.ms.weatherservice.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.GetTokenDto;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.dto.UserDto;
import uz.ms.weatherservice.model.User;
import uz.ms.weatherservice.repository.UserRepository;
import uz.ms.weatherservice.security.JwtUtil;
import uz.ms.weatherservice.service.UserService;
import uz.ms.weatherservice.service.mapper.UserMapper;

import java.util.List;

import static uz.ms.weatherservice.config.AppStatusCodes.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final Gson gson;

    @Override
    public Mono<ResponseDto<UserDto>> addUser(UserDto dto) {
        return userRepository.findByEmail(dto.getEmail())
                .map(usr ->
                        ResponseDto.<UserDto>builder()
                                .code(UNEXPECTED_ERROR_CODE)
                                .message("User with this email " + dto.getEmail() + " already exists")
                                .build())
                .switchIfEmpty(userRepository.save(userMapper.toEntity(dto))
                        .map(savedUser ->
                                ResponseDto.<UserDto>builder()
                                        .success(true)
                                        .data(userMapper.toDto(savedUser))
                                        .message("OK")
                                        .build())
                );
    }

    @Override
    public Mono<ResponseDto<UserDto>> editUser(UserDto userDto) {
        Mono<User> user = userRepository.findById(userDto.getId())
                .map(usr -> {
                    usr.setFirstName(userDto.getFirstName());
                    usr.setLastName(userDto.getLastName());
                    usr.setPhoneNumber(userDto.getPhoneNumber());
                    usr.setEmail(userDto.getEmail());
                    return usr;
                });
        return user.flatMap(userRepository::save)
                .map(usr -> ResponseDto.<UserDto>builder()
                        .message("OK")
                        .success(true)
                        .data(userMapper.toDto(usr))
                        .build())
                        .defaultIfEmpty(ResponseDto.<UserDto>builder()
                        .message("User with ID " + userDto.getId() + " is not found")
                        .code(NOT_FOUND_ERROR_CODE)
                        .data(userDto)
                        .build());
    }

    @Override
    public Mono<ResponseDto<String>> getToken(GetTokenDto getTokenDto) {
        Mono<User> user = userRepository.findByEmail(getTokenDto.getEmail());
        return user.filter(usr ->
                        passwordEncoder.matches(getTokenDto.getPassword(), usr.getPassword()))
                .map(usr -> ResponseDto.<String>builder()
                        .success(true)
                        .message("OK")
                        .data(jwtUtil.generateToken(gson.toJson(usr), List.of(String.valueOf(usr.getRole()))))
                        .build())
                .defaultIfEmpty(ResponseDto.<String>builder()
                        .code(1)
                        .message("Email or password is incorrect")
                        .build());


    }

    public Flux<UserDto> getAll() {
        return userRepository.findAll()
                .map(userMapper::toDto);
    }
}
