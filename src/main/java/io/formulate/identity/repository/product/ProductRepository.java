package io.formulate.identity.repository.product;

import io.formulate.identity.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findAllByTenant_Id(Long tenantId);

  @Modifying
  @Query(value = "delete from products where tenant_id = :tenantId", nativeQuery = true)
  void deleteAllByTenantId(@Param("tenantId") Long tenantId);
}
