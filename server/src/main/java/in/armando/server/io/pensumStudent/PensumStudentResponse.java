package in.armando.server.io.pensumStudent;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PensumStudentResponse {
    private Long id;
    private Long studentId;
    private Long pensumId;
    private Long assignedBy;
    private LocalDateTime createdAt; 
    private LocalDateTime updatedAt; 
}
