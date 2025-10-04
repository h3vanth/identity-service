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

  @ManyToMany(mappedBy = "permissions")
  private List<Role> roles;

  @ManyToOne
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  public Permission(PermissionView permissionView, Tenant tenant) {
    id = permissionView.getId();
    name = permissionView.getName();

    this.tenant = tenant;
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
