package io.formulate.identity.repository.role;

import io.formulate.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long>, CustomRoleRepository {
  @Modifying
  @Query(value = "delete from roles where tenant_id = :tenantId", nativeQuery = true)
  void deleteAllByTenantId(@Param("tenantId") Long tenantId);
}

interface CustomRoleRepository {}
