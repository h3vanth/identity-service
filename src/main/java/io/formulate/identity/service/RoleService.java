package io.formulate.identity.service;

import io.formulate.identity.entity.Role;
import io.formulate.identity.model.RoleView;
import io.formulate.identity.repository.role.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;

  public List<RoleView> upsertRoles(String tenantId, List<RoleView> roles) {
    return Role.toRoleViews(
        roleRepository.saveAll(
            roles.stream().map(roleView -> new Role(tenantId, roleView)).toList()));
  }

  @Transactional
  public void deleteRoles(String tenantId, List<Long> roleIds) {
    List<Role> roles = roleRepository.findAllById(roleIds);

    for (Role role : roles) {
      role.getPermissions().forEach(permission -> permission.getRoles().remove(role));
      role.getUsers().forEach(user -> user.getRoles().remove(role));
    }

    roleRepository.deleteAllById(roleIds);
  }
}
