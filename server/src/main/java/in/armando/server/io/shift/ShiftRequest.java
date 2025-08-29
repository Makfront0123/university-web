package in.armando.server.io.shift;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShiftRequest {
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
