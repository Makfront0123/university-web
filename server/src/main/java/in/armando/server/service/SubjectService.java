package in.armando.server.service;

import java.util.List;

import in.armando.server.io.subject.SubjectRequest;
import in.armando.server.io.subject.SubjectResponse;

public interface SubjectService {
    SubjectResponse createSubject(SubjectRequest subject);

    SubjectResponse getSubjectById(Long id);

    SubjectResponse getSubjectByCode(String code);

    List<SubjectResponse> getAllSubjects();

    SubjectResponse deleteSubject(Long id);

    SubjectResponse updateSubject(Long id, SubjectRequest subject);

}
