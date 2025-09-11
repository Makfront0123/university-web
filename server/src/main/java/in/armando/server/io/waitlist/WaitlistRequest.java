package in.armando.server.io.waitlist;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitlistRequest {
    private Long studentId;  
    private Long courseId;   
    private Integer position;
}
