package in.armando.server.io.shift;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftResponse {
    private Long id;
    private String name;
    private LocalTime startTime;   
    private LocalTime endTime;    
    private String dayOfWeek;
    private String message;
}
