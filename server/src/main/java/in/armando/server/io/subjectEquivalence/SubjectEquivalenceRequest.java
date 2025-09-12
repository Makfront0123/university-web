package in.armando.server.io.subjectEquivalence;

import lombok.Data;

@Data
public class SubjectEquivalenceRequest {
    private Long subjectId;
    private Long equivalentSubjectId;
}
