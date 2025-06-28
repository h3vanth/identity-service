package io.formulate.identity.service;

import io.formulate.identity.entity.Permission;
import io.formulate.identity.model.PermissionView;
import io.formulate.identity.repository.permission.PermissionRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {
  private final PermissionRepository permissionRepository;

  public List<PermissionView> createPermissions(String tenantId, List<PermissionView> permissions) {
    return Permission.toPermissionViews(
        permissionRepository.saveAll(
            permissions.stream()
                .map(permissionView -> new Permission(tenantId, permissionView))
                .toList()));
  }

  public List<PermissionView> updatePermissionNames(
      String tenantId, List<PermissionView> permissionViews) {
    List<Permission> permissions =
        permissionRepository.findAllById(
            permissionViews.stream().map(PermissionView::getId).toList());
    Map<Long, String> nameById =
        permissionViews.stream()
            .collect(Collectors.toMap(PermissionView::getId, PermissionView::getName));

    for (Permission permission : permissions) {
      permission.setName(nameById.get(permission.getId()));
    }

    return Permission.toPermissionViews(permissionRepository.saveAll(permissions));
  }
}
