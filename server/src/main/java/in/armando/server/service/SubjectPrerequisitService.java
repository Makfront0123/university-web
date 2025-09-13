package in.armando.server.service;

import java.util.List;

import in.armando.server.io.subjectPrerequisit.SubjectPrerequisitRequest;
import in.armando.server.io.subjectPrerequisit.SubjectPrerequisitResponse;

public interface SubjectPrerequisitService {
    SubjectPrerequisitResponse addSubjectPrerequisit(SubjectPrerequisitRequest request);

    SubjectPrerequisitResponse deleteSubjectPrerequisit(Long request);

    SubjectPrerequisitResponse getSubjectPrerequisit(Long id);

    SubjectPrerequisitResponse updateSubjectPrerequisit(SubjectPrerequisitRequest request);

    List<SubjectPrerequisitResponse> getSubjectPrerequisits();
}
