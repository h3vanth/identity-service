package io.formulate.identity.entity;

import io.formulate.identity.model.AbstractUserView;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
// If the class is not abstract, hibernate would create a table for InheritanceType.TABLE_PER_CLASS.
public abstract class AbstractUser {
  @Id
  @GeneratedValue
  @Column(updatable = false, nullable = false)
  protected Long id;

  @Column(updatable = false, nullable = false)
  protected String email;

  @Column(updatable = false, nullable = false, unique = true)
  protected String username;

  // JPA expects the version field (@Version) to be defined in the root (base) class of the entity
  // hierarchy.
  @Version
  @Column(nullable = false)
  private Long version;

  @CreatedDate
  @Column(nullable = false)
  protected LocalDateTime createTime;

  @Column(name = "tenant_id", updatable = false, nullable = false)
  protected String tenantId;

  @ManyToMany(mappedBy = "users")
  protected List<Role> roles;

  @ManyToMany
  @JoinTable(name = "users_products")
  protected List<Product> products;

  public AbstractUser(String tenantId, AbstractUserView abstractUserView) {
    id = abstractUserView.getId();
    email = abstractUserView.getEmail();
    username = abstractUserView.getUsername();
    roles =
        Optional.ofNullable(abstractUserView.getRoles())
            .map(
                roleViews ->
                    roleViews.stream()
                        .map(roleView -> new Role(tenantId, roleView))
                        .collect(Collectors.toList()))
            .orElse(null);
    products =
        Optional.ofNullable(abstractUserView.getProducts())
            .map(
                productViews ->
                    productViews.stream()
                        .map(productView -> new Product(tenantId, productView))
                        .collect(Collectors.toList()))
            .orElse(null);

    this.tenantId = tenantId;
  }

  public AbstractUserView toAbstractUserView() {
    return switch (this) {
      case TenantUser tenantUser -> tenantUser.toAbstractUserView();
      case IntegrationUser integrationUser -> integrationUser.toAbstractUserView();
      default -> null;
    };
  }
}
