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

import in.armando.server.io.ApiResponse;
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
    public ResponseEntity<ApiResponse<TuitionPaymentResponse>> create(@RequestBody TuitionPaymentRequest request) {
        TuitionPaymentResponse response = tuitionPaymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Pago de matrícula creado exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TuitionPaymentResponse>> getById(@PathVariable Long id) {
        TuitionPaymentResponse response = tuitionPaymentService.getPaymentById(id);
        return ResponseEntity.ok(new ApiResponse<>("Pago de matrícula encontrado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TuitionPaymentResponse>>> getAll() {
        List<TuitionPaymentResponse> response = tuitionPaymentService.getAllPayments();
        return ResponseEntity.ok(new ApiResponse<>("Lista de pagos de matrícula", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TuitionPaymentResponse>> update(
            @PathVariable Long id,
            @RequestBody TuitionPaymentRequest request) {
        TuitionPaymentResponse response = tuitionPaymentService.updatePayment(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Pago de matrícula actualizado exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        tuitionPaymentService.deletePayment(id);
        return ResponseEntity.ok(new ApiResponse<>("Pago de matrícula eliminado exitosamente", null));
    }
}
