package in.armando.server.io.subject;

import lombok.Data;

@Data
public class SubjectRequest {
    private String name;
    private String code;
    private Integer credits;
}
