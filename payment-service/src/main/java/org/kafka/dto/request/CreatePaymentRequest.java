package org.kafka.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreatePaymentRequest {

    @NotNull
    private Float amount;

    @NotNull
    private Date paymentDate;

    @NotNull
    private Long paymentMethodId;
}
