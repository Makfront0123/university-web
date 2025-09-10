package in.armando.server.service;

import java.util.List;

import in.armando.server.io.pensumSubject.PensumSubjectRequest;
import in.armando.server.io.pensumSubject.PensumSubjectResponse;

public interface PensumSubjectService {
    PensumSubjectResponse create(PensumSubjectRequest request);

    PensumSubjectResponse getById(Long id);

    void delete(Long id);

    List<PensumSubjectResponse> getAll();

    PensumSubjectResponse update(Long id, PensumSubjectRequest request);
}
