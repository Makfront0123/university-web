package in.armando.server.io.professors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRequest {
    private Long userId;
    private String name;
    private String lastName;
    private String role;
    private String email;
}
