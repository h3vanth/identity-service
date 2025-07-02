package io.formulate.identity.repository.product;

import io.formulate.identity.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findAllByTenantId(String tenantId);
}
