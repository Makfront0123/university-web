package in.armando.server.io.graduationRequeriment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraduationRequerimentRequest {
    private String name;
    private String description;
    private Integer requiredCredits;
    private Boolean mandatory;
    private Integer mandatoryCredits;  
    private Long pensumId;
}
