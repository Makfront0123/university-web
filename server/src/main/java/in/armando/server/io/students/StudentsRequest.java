package in.armando.server.io.students;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor    
@AllArgsConstructor  
public class StudentsRequest {
    private Long userId;     
    private String code;
}
