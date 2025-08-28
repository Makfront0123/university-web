package in.armando.server.io.enrollment;

import lombok.Data;

@Data
public class EnrollmentResponse {
    private Long id;
    private Long courseId;
    private Long studentId;
    private Integer grade;
    private String status;
}
