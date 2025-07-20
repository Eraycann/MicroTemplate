package org.kafka.dto.response;

import lombok.Data;
import org.kafka.dto.BaseAuditDto;

import java.util.Date;

@Data
public class PaymentResponse /*extends BaseAuditDto*/ {
    private Long id;
    private Float amount;
    private Date paymentDate;
    private String paymentMethodName;
}