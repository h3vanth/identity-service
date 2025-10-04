package io.formulate.identity.repository.permission;

import io.formulate.identity.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
  @Modifying
  @Query(value = "delete from permissions where tenant_id = :tenantId", nativeQuery = true)
  void deleteAllByTenantId(@Param("tenantId") Long tenantId);
}
