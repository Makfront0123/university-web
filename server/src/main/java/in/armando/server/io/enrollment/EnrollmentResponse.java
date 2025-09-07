package in.armando.server.io.enrollment;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResponse {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDate enrollmentDate;
    private Double grade;
    private String status;
    private String message;  
}
