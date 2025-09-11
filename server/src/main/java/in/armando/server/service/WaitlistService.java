package in.armando.server.service;

import java.util.List;

import in.armando.server.io.waitlist.WaitlistRequest;
import in.armando.server.io.waitlist.WaitlistResponse;

public interface WaitlistService {
    WaitlistResponse create(WaitlistRequest request);

    WaitlistResponse getById(Long id);

    WaitlistResponse delete(Long id);

    WaitlistResponse update(Long id, WaitlistRequest request);

    List<WaitlistResponse> getAll();

}
