package io.formulate.identity.controller;

import io.formulate.identity.model.ProductView;
import io.formulate.identity.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenants/{tenantId}/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping
  public List<ProductView> upsertProducts(
      @PathVariable("tenantId") String tenantId, @RequestBody List<ProductView> products) {
    return productService.upsertProducts(tenantId, products);
  }

  @GetMapping
  public List<ProductView> getProducts(@PathVariable("tenantId") String tenantId) {
    return productService.getProducts(tenantId);
  }
}
