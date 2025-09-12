package in.armando.server.service;

import java.util.List;

import in.armando.server.io.pensumStudent.PensumStudentRequest;
import in.armando.server.io.pensumStudent.PensumStudentResponse;

public interface PensumStudentService {
    PensumStudentResponse createPensumStudent(PensumStudentRequest request);

    PensumStudentResponse getPensumStudentById(Long id);

    void deletePensumStudent(Long id);

    PensumStudentResponse updatePensumStudent(Long id, PensumStudentRequest request);

    List<PensumStudentResponse> getAll();
}