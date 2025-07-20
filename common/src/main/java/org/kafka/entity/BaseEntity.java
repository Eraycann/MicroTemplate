package org.kafka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * BaseEntity: Tüm entity'lerin ortak temel yapısıdır.
 * - id alanı burada olmuyor, çünkü ID projeye göre değişebilir.
 * - Optimistic Locking için @Version alanı içerir.
 * - Soft Delete için isDeleted alanı içerir.
 */
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseEntity {

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted = false;

    /**
     * Soft delete işlemi için entity üzerinde silme yapıldığı zaman
     * bu alan true olarak set edilir.
     * Physical delete yerine bunu kullanırız.
     */
    public void softDelete() {
        this.isDeleted = true;
    }

    public boolean isDeleted() {
        return Boolean.TRUE.equals(isDeleted);
    }
}
