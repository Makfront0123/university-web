package in.armando.server.io.waitlist;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaitlistResponse {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Integer position;
    private String message;
    private LocalDateTime addedDate;
}
