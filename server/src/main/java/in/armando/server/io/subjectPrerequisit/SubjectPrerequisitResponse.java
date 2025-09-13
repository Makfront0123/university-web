package in.armando.server.io.subjectPrerequisit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectPrerequisitResponse {
    private Long id;
    private Long subjectId;
    private Long requiredSubjectId;
}
