package io.formulate.identity.service;

import io.formulate.identity.entity.Permission;
import io.formulate.identity.entity.Role;
import io.formulate.identity.model.RoleView;
import io.formulate.identity.repository.role.RoleRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;

  public List<RoleView> createRoles(String tenantId, List<RoleView> roles) {
    return Role.toRoleViews(
        roleRepository.saveAll(
            roles.stream().map(roleView -> new Role(tenantId, roleView)).toList()));
  }

  public List<RoleView> updateRoles(String tenantId, List<RoleView> roleViews) {
    List<Role> roles = roleRepository.findAllById(roleViews.stream().map(RoleView::getId).toList());
    Map<Long, RoleView> roleById =
        roleViews.stream().collect(Collectors.toMap(RoleView::getId, Function.identity()));

    for (Role role : roles) {
      RoleView roleView = roleById.get(role.getId());
      role.setName(roleView.getName());
      role.setPermissions(
          roleView.getPermissions().stream()
              .map(permissionView -> new Permission(tenantId, permissionView))
              .collect(Collectors.toList()));
    }

    return Role.toRoleViews(roleRepository.saveAll(roles));
  }
}
