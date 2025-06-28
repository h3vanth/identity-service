package io.formulate.identity.repository.permission;

import io.formulate.identity.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {}
