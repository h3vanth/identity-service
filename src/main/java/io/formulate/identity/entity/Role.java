package io.formulate.identity.entity;

import io.formulate.identity.model.RoleView;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "name"}))
@Getter
@Setter
@NoArgsConstructor
public class Role {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(
      name = "roles_permissions",
      // Included to demonstrate how to customize column names
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private List<Permission> permissions;

  @ManyToMany
  @JoinTable(name = "users_roles")
  private List<AbstractUser> users;

  @Column(name = "tenant_id", updatable = false, nullable = false)
  private String tenantId;

  public Role(String tenantId, RoleView roleView) {
    id = roleView.getId();
    name = roleView.getName();
    permissions =
        Optional.ofNullable(roleView.getPermissions())
            .map(
                permissionViews ->
                    permissionViews.stream()
                        .map(permissionView -> new Permission(tenantId, permissionView))
                        .collect(Collectors.toList()))
            .orElse(null);

    this.tenantId = tenantId;
  }

  public RoleView toRoleView() {
    return new RoleView(id, name, Permission.toPermissionViews(permissions));
  }

  public static List<RoleView> toRoleViews(List<Role> roles) {
    return Optional.ofNullable(roles)
        .map(r -> r.stream().map(Role::toRoleView).toList())
        .orElse(null);
  }
}
