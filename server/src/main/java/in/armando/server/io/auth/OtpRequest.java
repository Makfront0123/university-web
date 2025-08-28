package in.armando.server.io.auth;

import in.armando.server.io.professors.ProfessorRequest;
import in.armando.server.io.students.StudentsRequest;
import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String otp;
    private StudentsRequest studentProfileData;
    private ProfessorRequest professorProfileData;

}
