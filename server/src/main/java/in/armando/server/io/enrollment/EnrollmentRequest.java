package in.armando.server.io.enrollment;

import lombok.Data;

@Data
public class EnrollmentRequest {
    private Long courseId;
    private Long studentId;
    private Double grade;
    private String status;
}
