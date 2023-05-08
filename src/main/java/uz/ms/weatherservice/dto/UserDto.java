package uz.ms.weatherservice.dto;

import lombok.*;
import uz.ms.weatherservice.security.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private UserRole role;
}
