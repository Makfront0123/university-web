package in.armando.server.io.course;

import in.armando.server.io.professors.ProfessorResponse;
import in.armando.server.io.subject.SubjectResponse;
import lombok.Data;

@Data
public class CourseResponse {
    private Long id;
    private String classRoom;
    private Integer capacity;
    private SubjectResponse subject;
    //private SemesterResponse semester;
    //private ShiftResponse shift;
    private ProfessorResponse professor;
}
