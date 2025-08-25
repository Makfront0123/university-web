package in.armando.server.io.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequest {
    private String name;
    private String lastName;
    private String role;
    private String email;
    private String password;
    private Long roleId;
}
