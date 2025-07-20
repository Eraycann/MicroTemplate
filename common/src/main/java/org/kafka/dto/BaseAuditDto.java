package org.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * BaseAuditDto:
 * DTO’larda audit (oluşturma/güncelleme) bilgilerini tutar.
 */
// Entity'si BaseAuditEntity olanlar için, ResponseDTO'su da BaseAuditDto olması için yazıldı.
// Bunu genelde kullanmak best practice değil.
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseAuditDto {
    protected String createdBy;
    protected LocalDateTime createdAt;
    protected String updatedBy;
    protected LocalDateTime updatedAt;
}
