package in.armando.server.io.pensumSubject;

import in.armando.server.io.pensum.PensumResponse;
import in.armando.server.io.subject.SubjectResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PensumSubjectResponse {
    private Long id;
    private PensumResponse pensumId;
    private SubjectResponse subjectId;
    private String message;
}
