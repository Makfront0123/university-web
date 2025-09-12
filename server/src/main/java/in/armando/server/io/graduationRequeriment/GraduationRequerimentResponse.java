package in.armando.server.io.graduationRequeriment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GraduationRequerimentResponse {
    private Long id;
    private String name;
    private String description;
    private Integer requiredCredits;
    private boolean mandatory;
    private Long pensumId;
}
