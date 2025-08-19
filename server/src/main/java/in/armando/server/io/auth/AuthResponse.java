package in.armando.server.io.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Getter
@AllArgsConstructor
public class AuthResponse {
    private final String token;
    private final String role;
    private final String email;
    private final boolean verified;
}
