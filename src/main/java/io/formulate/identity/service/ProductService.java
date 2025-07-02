package io.formulate.identity.service;

import io.formulate.identity.entity.Product;
import io.formulate.identity.model.ProductView;
import io.formulate.identity.repository.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public List<ProductView> upsertProducts(String tenantId, List<ProductView> products) {
    return Product.toProductViews(
        productRepository.saveAll(
            products.stream().map(productView -> new Product(tenantId, productView)).toList()));
  }

  public List<ProductView> getProducts(String tenantId) {
    return Product.toProductViews(productRepository.findAllByTenantId(tenantId));
  }
}
