package io.formulate.identity.entity;

import io.formulate.identity.model.BaseUserView;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
// If the class is not abstract, hibernate would create a table.
public abstract class BaseUser {
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

  @ManyToMany
  @JoinTable(name = "users_roles")
  protected List<Role> roles;

  public BaseUser(String tenantId, BaseUserView baseUserView) {
    id = baseUserView.getId();
    email = baseUserView.getEmail();
    username = baseUserView.getUsername();
    roles =
        Optional.ofNullable(baseUserView.getRoles())
            .map(
                roleViews ->
                    roleViews.stream()
                        .map(roleView -> new Role(tenantId, roleView))
                        .collect(Collectors.toList()))
            .orElse(null);

    this.tenantId = tenantId;
  }

  public abstract BaseUserView toBaseUserView();
}
