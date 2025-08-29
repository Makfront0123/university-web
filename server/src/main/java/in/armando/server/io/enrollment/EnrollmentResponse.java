package in.armando.server.io.enrollment;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentResponse {
    private Long id;
    private Long courseId;
    private Long studentId;
    private Double grade;
    private String status;
    private LocalDate enrollmentDate;
}
