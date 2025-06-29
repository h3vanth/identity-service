package io.formulate.identity.entity;

import io.formulate.identity.model.PermissionView;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "permissions",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
public class Permission {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "tenant_id", updatable = false, nullable = false)
  private String tenantId;

  public Permission(String tenantId, PermissionView permissionView) {
    id = permissionView.getId();
    name = permissionView.getName();

    this.tenantId = tenantId;
  }

  public PermissionView toPermissionView() {
    return new PermissionView(id, name);
  }

  public static List<PermissionView> toPermissionViews(List<Permission> permissions) {
    return Optional.ofNullable(permissions)
        .map(perms -> perms.stream().map(Permission::toPermissionView).toList())
        .orElse(null);
  }
}
