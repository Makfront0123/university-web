package in.armando.server.io.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private Long roleId; 
}
