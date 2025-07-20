package org.kafka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.kafka.security.util.AuthUtil;

import java.time.LocalDateTime;

/**
 * BaseAuditEntity:
 * Tüm entity’lere oluşturulma ve güncellenme bilgilerini otomatik ekler.
 * - createdAt / createdBy: İlk kayıt zamanı ve kullanıcı.
 * - updatedAt / updatedBy: Son güncelleme zamanı ve kullanıcı.
 * - AuthUtil ile oturumdaki kullanıcıdan alınır.
 * - BaseEntity’den türediği için soft delete ve versioning desteği de içerir.
 */

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseAuditEntity extends BaseEntity{

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = AuthUtil.getCurrentUserId()
                .map(Long::parseLong)
                .orElse(null);  // veya 0L gibi bir default id
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = AuthUtil.getCurrentUserId()
                .map(Long::parseLong)
                .orElse(null);
    }
}
