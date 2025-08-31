package in.armando.server.io.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequest {
    private String name;
    private String code;
    private Integer credits;
}
