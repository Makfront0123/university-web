package in.armando.server.io.professors;

import in.armando.server.io.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorResponse {
    private Long id;
    private String code;
    private UserResponse user;
}
