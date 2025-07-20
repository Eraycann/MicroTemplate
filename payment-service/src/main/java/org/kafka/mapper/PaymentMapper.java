package org.kafka.mapper;

import org.kafka.dto.request.CreatePaymentRequest;
import org.kafka.dto.request.UpdatePaymentRequest;
import org.kafka.dto.response.PaymentResponse;
import org.kafka.model.Payment;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "paymentMethod.name", target = "paymentMethodName")
    PaymentResponse toResponse(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    Payment toEntity(CreatePaymentRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdatePaymentRequest dto, @MappingTarget Payment entity);
}