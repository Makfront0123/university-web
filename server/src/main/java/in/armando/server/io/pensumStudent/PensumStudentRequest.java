package in.armando.server.io.pensumStudent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PensumStudentRequest {
    private Long studentId;
    private Long pensumId;
    private Long assignedBy;
}