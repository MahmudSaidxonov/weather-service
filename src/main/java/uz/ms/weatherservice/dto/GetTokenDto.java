package uz.ms.weatherservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTokenDto {
    private String email;
    private String password;
}
