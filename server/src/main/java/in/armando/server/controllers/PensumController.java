package in.armando.server.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.armando.server.io.pensum.PensumRequest;
import in.armando.server.io.pensum.PensumResponse;
import in.armando.server.service.PensumService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pensum")
@RequiredArgsConstructor
public class PensumController {
    private final PensumService pensumService;

    @PostMapping
    public PensumResponse createPensum(@RequestBody PensumRequest request) {
        return pensumService.create(request);
    }

    @GetMapping
    public List<PensumResponse> getAllPensum() {
        return pensumService.getAll();
    }

    @GetMapping("/{id}")
    public PensumResponse getPensumById(@PathVariable Long id) {
        return pensumService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePensum(@PathVariable Long id) {
        pensumService.delete(id);
    }

    @PatchMapping("/{id}")
    public PensumResponse updatePensum(@PathVariable Long id, @RequestBody PensumRequest request) {
        return pensumService.update(id, request);
    }
}
