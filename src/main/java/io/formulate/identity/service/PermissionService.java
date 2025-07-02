package io.formulate.identity.service;

import io.formulate.identity.entity.Permission;
import io.formulate.identity.model.PermissionView;
import io.formulate.identity.repository.permission.PermissionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PermissionService {
  private final PermissionRepository permissionRepository;

  public List<PermissionView> upsertPermissions(String tenantId, List<PermissionView> permissions) {
    return Permission.toPermissionViews(
        permissionRepository.saveAll(
            permissions.stream()
                .map(permissionView -> new Permission(tenantId, permissionView))
                .toList()));
  }

  // Spring Boot automatically enables transaction management. No need for
  // @EnableTransactionManagement.
  // @Transactional ensures that the removal of the permission from roles is properly tracked and
  // flushed to the database when the transaction completes.
  @Transactional
  public void deletePermissions(String tenantId, List<Long> permissionIds) {
    List<Permission> permissions = permissionRepository.findAllById(permissionIds);

    for (Permission permission : permissions) {
      // Set would be better here
      permission.getRoles().forEach(role -> role.getPermissions().remove(permission));
    }

    permissionRepository.deleteAllById(permissionIds);
  }
}
