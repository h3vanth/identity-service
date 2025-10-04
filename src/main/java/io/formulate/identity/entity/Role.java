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

  @ManyToOne
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  public Role(RoleView roleView, Tenant tenant) {
    id = roleView.getId();
    name = roleView.getName();
    permissions =
        Optional.ofNullable(roleView.getPermissions())
            .map(
                permissionViews ->
                    permissionViews.stream()
                        .map(permissionView -> new Permission(permissionView, tenant))
                        .collect(Collectors.toList()))
            .orElse(null);

    this.tenant = tenant;
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
