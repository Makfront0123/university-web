package in.armando.server.service;

import java.util.List;

import in.armando.server.io.semester.SemesterRequest;
import in.armando.server.io.semester.SemesterResponse;

public interface SemesterService {
    SemesterResponse createSemester(SemesterRequest semesterRequest);
    SemesterResponse getSemesterById(Long id);
    SemesterResponse updateSemester(Long id, SemesterRequest semesterRequest);
    SemesterResponse deleteSemester(Long id);
    List<SemesterResponse> getAllSemester();
}
