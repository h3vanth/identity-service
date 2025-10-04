package io.formulate.identity.service;

import io.formulate.identity.entity.Product;
import io.formulate.identity.entity.Tenant;
import io.formulate.identity.model.ProductView;
import io.formulate.identity.repository.product.ProductRepository;
import java.util.List;

import io.formulate.identity.repository.tenant.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final TenantRepository tenantRepository;

  public List<ProductView> upsertProducts(String tenantId, List<ProductView> products) {
    Tenant tenant = tenantRepository.findById(Long.valueOf(tenantId)).orElseThrow();
    return Product.toProductViews(
        productRepository.saveAll(
            products.stream().map(productView -> new Product(productView, tenant)).toList()));
  }

  public List<ProductView> getProducts(String tenantId) {
    return Product.toProductViews(productRepository.findAllByTenant_Id(Long.valueOf(tenantId)));
  }
}
