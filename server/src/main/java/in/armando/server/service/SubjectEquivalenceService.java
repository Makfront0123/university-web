package in.armando.server.service;

import java.util.List;

import in.armando.server.io.subjectEquivalence.SubjectEquivalenceRequest;
import in.armando.server.io.subjectEquivalence.SubjectEquivalenceResponse;

public interface SubjectEquivalenceService {
    SubjectEquivalenceResponse addSubjectEquivalence(SubjectEquivalenceRequest request);

    SubjectEquivalenceResponse deleteSubjectEquivalence(Long id);

    SubjectEquivalenceResponse getSubjectEquivalence(Long id);

    SubjectEquivalenceResponse updateSubjectEquivalence(Long id,SubjectEquivalenceRequest request);

    SubjectEquivalenceResponse getSubjectEquivalences(SubjectEquivalenceRequest request);

    List<SubjectEquivalenceResponse> getSubjectEquivalences();
}
