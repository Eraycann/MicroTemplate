package org.kafka.service;

import org.kafka.dto.request.CreatePaymentRequest;
import org.kafka.dto.request.UpdatePaymentRequest;
import org.kafka.dto.response.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPaymentService {

    PaymentResponse create(CreatePaymentRequest request);
    PaymentResponse update(Long id, UpdatePaymentRequest request);
    PaymentResponse getById(Long id);
    Page<PaymentResponse> getAll(Pageable pageable);
    void delete(Long id);
    Page<PaymentResponse> getByAmountGreaterThan(Float minAmount, Pageable pageable);
}
