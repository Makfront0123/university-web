package in.armando.server.io.semester;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterRequest {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
