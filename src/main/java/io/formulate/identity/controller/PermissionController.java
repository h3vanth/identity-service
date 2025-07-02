package io.formulate.identity.controller;

import io.formulate.identity.model.PermissionView;
import io.formulate.identity.service.PermissionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tenants/{tenantId}/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
  private final PermissionService permissionService;

  @PostMapping
  public List<PermissionView> upsertPermissions(
      @PathVariable("tenantId") String tenantId, @RequestBody List<PermissionView> permissions) {
    return permissionService.upsertPermissions(tenantId, permissions);
  }

  @DeleteMapping
  public void deletePermissions(
      @PathVariable("tenantId") String tenantId, @RequestBody List<Long> permissionIds) {
    permissionService.deletePermissions(tenantId, permissionIds);
  }
}
