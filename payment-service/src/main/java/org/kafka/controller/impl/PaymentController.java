package org.kafka.controller.impl;

import lombok.RequiredArgsConstructor;
import org.kafka.controller.IPaymentController;
import org.kafka.dto.request.CreatePaymentRequest;
import org.kafka.dto.request.UpdatePaymentRequest;
import org.kafka.dto.response.PaymentResponse;
import org.kafka.service.IPaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class PaymentController implements IPaymentController {

    private final IPaymentService paymentService;

    @Override
    public ResponseEntity<PaymentResponse> create(CreatePaymentRequest request) {
        return ResponseEntity.ok(paymentService.create(request));
    }

    @Override
    public ResponseEntity<PaymentResponse> update(Long id, UpdatePaymentRequest request) {
        return ResponseEntity.ok(paymentService.update(id, request));
    }

    @Override
    public ResponseEntity<PaymentResponse> getById(Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @Override
    public ResponseEntity<Page<PaymentResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(paymentService.getAll(pageable));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<PaymentResponse>> getByMinAmount(Float minAmount, Pageable pageable) {
        return ResponseEntity.ok(paymentService.getByAmountGreaterThan(minAmount, pageable));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin-only")
    public ResponseEntity<?> adminOnlyEndpoint() {
        return ResponseEntity.ok("Only admins can see this");
    }

}