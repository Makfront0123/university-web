package in.armando.server.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.armando.server.io.tuitionPayment.TuitionPaymentRequest;
import in.armando.server.io.tuitionPayment.TuitionPaymentResponse;
import in.armando.server.service.TuitionPaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class TuitionPaymentController {

    private final TuitionPaymentService tuitionPaymentService;

    @PostMapping
    public ResponseEntity<TuitionPaymentResponse> create(@RequestBody TuitionPaymentRequest request) {
        return ResponseEntity.ok(tuitionPaymentService.createPayment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TuitionPaymentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tuitionPaymentService.getPaymentById(id));
    }

    @GetMapping
    public ResponseEntity<List<TuitionPaymentResponse>> getAll() {
        return ResponseEntity.ok(tuitionPaymentService.getAllPayments());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TuitionPaymentResponse> update(@PathVariable Long id,
            @RequestBody TuitionPaymentRequest request) {
        return ResponseEntity.ok(tuitionPaymentService.updatePayment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tuitionPaymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
