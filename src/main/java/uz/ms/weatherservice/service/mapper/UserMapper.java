package uz.ms.weatherservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.ms.weatherservice.dto.UserDto;
import uz.ms.weatherservice.model.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CommonMapper<UserDto, User> {

    @Autowired
    protected PasswordEncoder encoder;

    @Mapping(target = "password", expression = "java(null)")
    public abstract UserDto toDto(User user);
    @Mapping(target = "password", expression = "java(encoder.encode(dto.getPassword()))")
    public abstract User toEntity(UserDto dto);
}
