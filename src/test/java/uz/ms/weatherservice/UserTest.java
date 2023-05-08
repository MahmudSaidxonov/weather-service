package uz.ms.weatherservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import uz.ms.weatherservice.dto.UserDto;
import uz.ms.weatherservice.rest.UserResources;
import uz.ms.weatherservice.service.impl.UserServiceImpl;


//@RunWith(SpringRunner.class)
//@WebFluxTest(UserResources.class)
//
//
public class UserTest {
//    @Autowired
//    private WebTestClient webTestClient;
//    @MockBean
//    private UserServiceImpl userService;
//
//    @Test
//    public void addUser() {
//        UserDto userDtoMono =
//                UserDto.builder()
//                        .id(2)
//                        .firstName("Mahmud")
//                        .lastName("Saidxonov")
//                        .phoneNumber("99 999 99 99")
//                        .email("test@gmail.com")
//                        .password("123")
//                        .build();
//
//        webTestClient.post()
//                .uri("/user/add-user")
//                .bodyValue(userDtoMono)
//                .exchange()
//
//                .expectStatus().isOk()
//                .expectHeader().contentType("application/json");
//    }

}
