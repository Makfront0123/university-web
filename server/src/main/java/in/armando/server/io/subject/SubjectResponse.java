package in.armando.server.io.subject;

import lombok.Data;

@Data
public class SubjectResponse {
    private Long id;
    private String name;
    private String code;
    private Integer credits;
}
