package in.armando.server.io.course;

import in.armando.server.io.professors.ProfessorResponse;
import in.armando.server.io.semester.SemesterResponse;
import in.armando.server.io.shift.ShiftResponse;
import in.armando.server.io.subject.SubjectResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String classRoom;
    private Integer capacity;
    private SubjectResponse subject;
    private SemesterResponse semester;
    private ShiftResponse shift;
    private ProfessorResponse professor;
}
