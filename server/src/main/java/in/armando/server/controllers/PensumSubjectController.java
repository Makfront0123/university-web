
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

import in.armando.server.io.pensumSubject.PensumSubjectRequest;
import in.armando.server.io.pensumSubject.PensumSubjectResponse;
import in.armando.server.service.PensumSubjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pensum-subject")
@RequiredArgsConstructor
public class PensumSubjectController {

    private final PensumSubjectService pensumSubjectService;

    @PostMapping
    public PensumSubjectResponse create(@RequestBody PensumSubjectRequest request) {
        return pensumSubjectService.create(request);
    }

    @GetMapping
    public List<PensumSubjectResponse> getAll() {
        return pensumSubjectService.getAll();
    }

    @GetMapping("/{id}")
    public PensumSubjectResponse getById(@PathVariable Long id) {
        return pensumSubjectService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pensumSubjectService.delete(id);
    }

    @PatchMapping("/{id}")
    public PensumSubjectResponse update(@PathVariable Long id, @RequestBody PensumSubjectRequest request) {
        return pensumSubjectService.update(id, request);
    }
}
