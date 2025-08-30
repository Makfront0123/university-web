package in.armando.server.io.course;

import lombok.Data;

@Data
public class CourseRequest {
    private String classRoom;
    private Integer capacity;

    private Long subjectId;
    private Long semesterId;
    private Long shiftId;
    private Long professorId;
}
