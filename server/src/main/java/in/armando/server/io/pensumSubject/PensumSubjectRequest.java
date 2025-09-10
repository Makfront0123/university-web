package in.armando.server.io.pensumSubject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PensumSubjectRequest {
    private Long pensumId;
    private Long subjectId;
}
