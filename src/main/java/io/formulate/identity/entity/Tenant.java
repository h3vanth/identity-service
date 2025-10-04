package io.formulate.identity.entity;

import io.formulate.identity.model.TenantView;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "tenant")
  private List<Property> properties;

  public Tenant(TenantView tenantView) {
    id = tenantView.getId();
    name = tenantView.getName();
  }

  public TenantView toTenantView() {
    return new TenantView(id, name, Property.toPropertyViews(properties));
  }

  public static List<TenantView> toTenantViews(List<Tenant> tenants) {
    return Optional.ofNullable(tenants)
        .map(tenantList -> tenantList.stream().map(Tenant::toTenantView).toList())
        .orElse(null);
  }
}
