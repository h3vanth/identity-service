package io.formulate.identity.entity;

import io.formulate.identity.model.ProductView;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "products",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
public class Product {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @ManyToOne
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  public Product(ProductView productView, Tenant tenant) {
    id = productView.getId();
    name = productView.getName();
    description = productView.getDescription();

    this.tenant = tenant;
  }

  public ProductView toProductView() {
    return new ProductView(id, name, description);
  }

  public static List<ProductView> toProductViews(List<Product> products) {
    return Optional.ofNullable(products)
        .map(productList -> productList.stream().map(Product::toProductView).toList())
        .orElse(null);
  }
}
