package in.armando.server.service;

import java.util.List;

import in.armando.server.io.graduationRequeriment.GraduationRequerimentRequest;
import in.armando.server.io.graduationRequeriment.GraduationRequerimentResponse;

public interface GraduationRequerimentService {
    GraduationRequerimentResponse create(GraduationRequerimentRequest request);

    GraduationRequerimentResponse getById(Long id);

    void delete(Long id);

    List<GraduationRequerimentResponse> getAll();

    GraduationRequerimentResponse update(Long id, GraduationRequerimentRequest request);
}
