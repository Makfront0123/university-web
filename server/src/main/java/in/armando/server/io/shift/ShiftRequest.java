package in.armando.server.io.shift;
 
import java.time.LocalTime;

import lombok.Data;

@Data
public class ShiftRequest {
    private String name;
    private LocalTime startTime; 
    private LocalTime endTime;   
    private String dayOfWeek;
}
