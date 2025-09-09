package in.armando.server.service;

import java.util.List;

import in.armando.server.io.pensum.PensumRequest;
import in.armando.server.io.pensum.PensumResponse;

public interface PensumService {
    PensumResponse create(PensumRequest request);

    PensumResponse update(Long id, PensumRequest request);

    void delete(Long id);

    PensumResponse getById(Long id);

    List<PensumResponse> getAll();
}
