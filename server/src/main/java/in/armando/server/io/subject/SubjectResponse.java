package in.armando.server.io.subject;

 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectResponse {
    private Long id;
    private String name;
    private String code;
    private Integer credits;
    private String message;
}
