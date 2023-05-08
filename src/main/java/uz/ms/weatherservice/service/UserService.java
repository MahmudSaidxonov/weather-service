package uz.ms.weatherservice.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.dto.GetTokenDto;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.dto.UserDto;

public interface UserService {

    /**
     * Postman: http://localhost:8080/user/add-user
     * Body:
     * {
     *     "firstName":"test",
     *     "lastName":"test",
     *     "phoneNumber":"333 33 33",
     *     "email":"test@gmail.com",
     *     "password":"123"
     * }
     */
    Mono<ResponseDto<UserDto>> addUser(UserDto dto);

    /**
     * Postman: http://localhost:8080/user/edit-user
     * Body:
     *    "id":2,
     *     "firstName":"test2",
     *     "lastName":"test2",
     *     "phoneNumber":"330 89 30",
     *     "email":"test2@gmail.com",
     *     "password":"111"
     */
    Mono<ResponseDto<UserDto>> editUser(UserDto usersDto);

    /**
     * Postman: http://localhost:8080/user/get-token
     * Body:
     *     {
     *     "email":"test@gmail.com",
     *     "password":"123"
     *     }
     */
    Mono<ResponseDto<String>> getToken(GetTokenDto getTokenDto);

    /**
     * Postman: http://localhost:8080/user/get-all-user
     */
    Flux<UserDto> getAll();

}
