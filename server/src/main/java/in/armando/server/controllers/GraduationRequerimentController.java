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

import in.armando.server.io.graduationRequeriment.GraduationRequerimentRequest;
import in.armando.server.io.graduationRequeriment.GraduationRequerimentResponse;
import in.armando.server.service.GraduationRequerimentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/graduation-requeriment")
@RequiredArgsConstructor
public class GraduationRequerimentController {
    private final GraduationRequerimentService service;

    @PostMapping
    public ResponseEntity<GraduationRequerimentResponse> create(@RequestBody GraduationRequerimentRequest request) {
        GraduationRequerimentResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public GraduationRequerimentResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<GraduationRequerimentResponse> getAll() {
        return service.getAll();
    }

    @PatchMapping("/{id}")
    public GraduationRequerimentResponse update(@PathVariable Long id,
            @RequestBody GraduationRequerimentRequest request) {
        return service.update(id, request);
    }
}
