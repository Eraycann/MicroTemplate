package org.kafka.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Kullanıcının rolünü (USER, ADMIN vs.) temsil eder.
 * Token'da yetkilendirme için gereklidir.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // Örn: ROLE_USER, ROLE_ADMIN
}
