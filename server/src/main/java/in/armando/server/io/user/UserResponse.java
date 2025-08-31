package in.armando.server.io.user;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import in.armando.server.io.role.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private RoleResponse role;
    private boolean verified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
