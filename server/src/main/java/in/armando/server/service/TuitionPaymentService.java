package in.armando.server.service;

import java.util.List;

import in.armando.server.io.tuitionPayment.TuitionPaymentRequest;
import in.armando.server.io.tuitionPayment.TuitionPaymentResponse;

public interface TuitionPaymentService {
    TuitionPaymentResponse createPayment(TuitionPaymentRequest payment);

    TuitionPaymentResponse updatePayment(Long id, TuitionPaymentRequest payment);

    void deletePayment(Long id);

    TuitionPaymentResponse getPaymentById(Long id);

    List<TuitionPaymentResponse> getAllPayments();

    List<TuitionPaymentResponse> getPaymentsByStudent(Long studentId);

    List<TuitionPaymentResponse> getPaymentsBySemester(Long semesterId);
}
