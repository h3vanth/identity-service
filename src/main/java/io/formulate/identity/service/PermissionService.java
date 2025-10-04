package io.formulate.identity.service;

import io.formulate.identity.entity.Permission;
import io.formulate.identity.entity.Tenant;
import io.formulate.identity.model.PermissionView;
import io.formulate.identity.repository.permission.PermissionRepository;
import io.formulate.identity.repository.tenant.TenantRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PermissionService {
  private final PermissionRepository permissionRepository;
  private final TenantRepository tenantRepository;

  public List<PermissionView> upsertPermissions(String tenantId, List<PermissionView> permissions) {
    Tenant tenant = tenantRepository.findById(Long.valueOf(tenantId)).orElseThrow();
    return Permission.toPermissionViews(
        permissionRepository.saveAll(
            permissions.stream()
                .map(permissionView -> new Permission(permissionView, tenant))
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
