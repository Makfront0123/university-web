package in.armando.server.io.students;

import in.armando.server.io.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsRequest {
    private Long userId;
    private String code;
    private UserResponse user;

    private String lastName;
    private String email;
    private String name;
}
