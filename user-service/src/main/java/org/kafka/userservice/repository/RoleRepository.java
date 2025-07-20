package org.kafka.userservice.repository;

import org.kafka.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Rol veritabanı işlemleri.
 * Roller veritabanında tutulur (ROLE_USER, ROLE_ADMIN vs.)
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
