package org.kafka.service.impl;


import lombok.RequiredArgsConstructor;
import org.kafka.dto.request.CreatePaymentRequest;
import org.kafka.dto.request.UpdatePaymentRequest;
import org.kafka.dto.response.PaymentResponse;
import org.kafka.exception.domain.PaymentDomainErrorCode;
import org.kafka.exception.domain.PaymentDomainException;
import org.kafka.exception.validation.PaymentValidationErrorCode;
import org.kafka.exception.validation.PaymentValidationException;
import org.kafka.mapper.PaymentMapper;
import org.kafka.model.Payment;
import org.kafka.model.PaymentMethod;
import org.kafka.repository.PaymentMethodRepository;
import org.kafka.repository.PaymentRepository;
import org.kafka.service.IPaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse create(CreatePaymentRequest request) {
        if (request.getPaymentDate().after(new Date())) {
            throw new PaymentValidationException(PaymentValidationErrorCode.PAYMENT_DATE_CANNOT_BE_IN_FUTURE);
        }

        PaymentMethod method = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new PaymentValidationException(PaymentValidationErrorCode.PAYMENT_METHOD_NOT_FOUND));

        if (!method.isActive()) {
            throw new PaymentValidationException(PaymentValidationErrorCode.PAYMENT_METHOD_IS_INACTIVE);
        }

        boolean duplicate = paymentRepository.existsByPaymentMethodIdAndPaymentDate(
                method.getId(), request.getPaymentDate());

        if (duplicate) {
            throw new PaymentValidationException(PaymentValidationErrorCode.SAME_DAY_DUPLICATE_PAYMENT);
        }

        Payment payment = paymentMapper.toEntity(request);
        payment.setPaymentMethod(method);
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponse update(Long id, UpdatePaymentRequest request) {
        if (request.getPaymentDate().after(new Date())) {
            throw new PaymentValidationException(PaymentValidationErrorCode.PAYMENT_DATE_CANNOT_BE_IN_FUTURE);
        }

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentDomainException(PaymentDomainErrorCode.PAYMENT_NOT_FOUND));

        PaymentMethod method = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new PaymentValidationException(PaymentValidationErrorCode.PAYMENT_METHOD_NOT_FOUND));

        if (!method.isActive()) {
            throw new PaymentValidationException(PaymentValidationErrorCode.PAYMENT_METHOD_IS_INACTIVE);
        }

        // Aynı gün ve aynı ödeme metodu için duplicate kontrolü,
        // kendisi hariç başka bir payment var mı kontrol et.
        boolean duplicate = paymentRepository.existsByPaymentMethodIdAndPaymentDateAndIdNot(
                method.getId(), request.getPaymentDate(), id);

        if (duplicate) {
            throw new PaymentValidationException(PaymentValidationErrorCode.SAME_DAY_DUPLICATE_PAYMENT);
        }

        // Mapstruct ile entity'yi güncelle
        paymentMapper.updateEntityFromDto(request, payment);
        payment.setPaymentMethod(method);

        Payment updatedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(updatedPayment);
    }


    @Override
    public PaymentResponse getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentDomainException(PaymentDomainErrorCode.PAYMENT_NOT_FOUND));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public Page<PaymentResponse> getAll(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(paymentMapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentDomainException(PaymentDomainErrorCode.PAYMENT_NOT_FOUND));
        paymentRepository.delete(payment);
    }

    @Override
    public Page<PaymentResponse> getByAmountGreaterThan(Float minAmount, Pageable pageable) {
        return paymentRepository.findByAmountGreaterThan(minAmount, pageable)
                .map(paymentMapper::toResponse);
    }
}