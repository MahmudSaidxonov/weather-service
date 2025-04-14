package uz.ms.weatherservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import uz.ms.weatherservice.dto.GetTokenDto;
import uz.ms.weatherservice.dto.ResponseDto;
import uz.ms.weatherservice.dto.UserDto;
import uz.ms.weatherservice.model.User;
import uz.ms.weatherservice.security.UserRole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext
@Slf4j
@Nested
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersIntegrationTests {

    private WebTestClient webTestClient;
    private static String token;

    @LocalServerPort
    private int randomServerPort;

    @BeforeEach
    public void setup() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + randomServerPort).build();
    }
    @Test
    @Order(2)
    public void addUser() {
        UserDto userDtoMono =
                UserDto.builder()
//                        .id(1)
                        .firstName("testqwe")
                        .lastName("testqwe")
                        .phoneNumber("99 999 99 991")
                        .email("testqwe@gmail.com")
                        .password("123")
                        .role(UserRole.ROLE_USER)
                        .build();

        WebTestClient.ResponseSpec responseSpec= webTestClient.post()
                .uri("/user/add-user")
                .bodyValue(userDtoMono)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json");

        responseSpec.expectBody(new ParameterizedTypeReference<ResponseDto<User>>() {})
                .value(r -> {
                    assertEquals(r.getCode(), 0);
                    assertNotNull(r.getData());
                    assertEquals(r.getData().getEmail(), userDtoMono.getEmail());
                });
    }
    @Order(3)
    @Test
    public void get_token_with_email_and_password(){
        ResponseDto<String> response = webTestClient.post()
                .uri("user/get-token")
                .bodyValue(GetTokenDto.builder()
                        .email("testqwe@gmail.com")
                        .password("123")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .returnResult(new ParameterizedTypeReference<ResponseDto<String>>() {
                })
                .getResponseBody()
                .doOnNext(res -> log.info("Response: {}", res))
                .blockFirst();

        assertNotNull(response, "Response is null");
        assertNotNull(response.getData(), "Token is not retrieved");

        token = response.getData();

        assertEquals(response.getCode(), 0, "Unexpected response code");
    }

}
