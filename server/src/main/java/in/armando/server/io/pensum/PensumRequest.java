package in.armando.server.io.pensum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PensumRequest {
    private String name;
    private String description;
 
}
