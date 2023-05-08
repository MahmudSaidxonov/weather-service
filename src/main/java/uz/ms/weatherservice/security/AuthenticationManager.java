package uz.ms.weatherservice.security;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import uz.ms.weatherservice.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;
    private final Gson gson;

    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            username = null;
            System.out.println(e.getMessage());
        }

        if (username != null && jwtUtil.validateToken(token)){
            List<String> role = jwtUtil.getClaim(token, "role", List.class);
            List<SimpleGrantedAuthority> roles = role.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
//            User user = gson.fromJson("{'email':'" + jwtUtil.getClaim(token, "sub", String.class) + "'}", User.class);
            User user = gson.fromJson(jwtUtil.getClaim(token, "sub", String.class), User.class);

            UsernamePasswordAuthenticationToken authenticatedUser =
                    new UsernamePasswordAuthenticationToken(user, null, roles);
            return Mono.just(authenticatedUser);
        }else {
            return Mono.empty();
        }
    }
}
