package io.formulate.identity.repository.property;

import io.formulate.identity.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
  List<Property> findAllByTenant_Id(Long tenantId);

  @Modifying
  @Query(value = "delete from properties where tenant_id = :tenantId", nativeQuery = true)
  void deleteAllByTenantId(@Param("tenantId") Long tenantId);
}
