package in.armando.server.io.enrollment;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentRequest {
    private Long studentId;
    private Long courseId;
    private LocalDate enrollmentDate;
    private Double grade;
    private String status;
}
