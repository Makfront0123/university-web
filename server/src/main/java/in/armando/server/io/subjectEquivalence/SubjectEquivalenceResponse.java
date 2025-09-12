package in.armando.server.io.subjectEquivalence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEquivalenceResponse {
    private Long id;
    private Long subjectId;
    private Long equivalentSubjectId;
}
