package org.kafka.controller;

import org.kafka.dto.request.CreatePaymentRequest;
import org.kafka.dto.request.UpdatePaymentRequest;
import org.kafka.dto.response.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/payments")
public interface IPaymentController {

    @PostMapping
    ResponseEntity<PaymentResponse> create(@RequestBody CreatePaymentRequest request);

    @PutMapping("/{id}")
    ResponseEntity<PaymentResponse> update(@PathVariable Long id, @RequestBody UpdatePaymentRequest request);

    @GetMapping("/{id}")
    ResponseEntity<PaymentResponse> getById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<Page<PaymentResponse>> getAll(Pageable pageable);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @GetMapping("/filter")
    ResponseEntity<Page<PaymentResponse>> getByMinAmount(
            @RequestParam("minAmount") Float minAmount,
            Pageable pageable
    );
}