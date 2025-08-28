package in.armando.server.io.transcript;

import in.armando.server.io.students.StudentsResponse;
import lombok.Data;

@Data
public class TranscriptResponse {
    private Long id;
    private Double promSem;
    private Double promTotal;
    private Integer earnedCredits;
    private Integer totalCredits;
    private StudentsResponse student;

}
