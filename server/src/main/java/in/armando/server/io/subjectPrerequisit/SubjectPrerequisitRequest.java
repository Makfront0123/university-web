package in.armando.server.io.subjectPrerequisit;

import lombok.Data;

@Data
public class SubjectPrerequisitRequest {
    private Long subjectId;
    private Long requiredSubjectId;
}
