package in.armando.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.armando.server.io.waitlist.WaitlistRequest;
import in.armando.server.io.waitlist.WaitlistResponse;
import in.armando.server.service.WaitlistService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/waitlist")
@RequiredArgsConstructor
public class WaitlistController {
    private final WaitlistService waitlistService;

    @PostMapping
    public ResponseEntity<WaitlistResponse> createWaitlist(@RequestBody WaitlistRequest request) {
        WaitlistResponse response = waitlistService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public WaitlistResponse getWaitlistById(@PathVariable Long id) {
        return waitlistService.getById(id);
    }

    @GetMapping
    public List<WaitlistResponse> getAllWaitlists() {
        return waitlistService.getAll();
    }

    @DeleteMapping("/{id}")
    public WaitlistResponse deleteWaitlist(@PathVariable Long id) {
        return waitlistService.delete(id);
    }

    @PatchMapping("/{id}")
    public WaitlistResponse updateWaitlist(
            @PathVariable Long id,
            @RequestBody WaitlistRequest request) {
        return waitlistService.update(id, request);
    }
}
