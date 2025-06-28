package io.formulate.identity.controller;

import io.formulate.identity.model.PermissionView;
import io.formulate.identity.model.RoleView;
import io.formulate.identity.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tenants/{tenantId}/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
  private final PermissionService permissionService;

  @PostMapping
  public List<PermissionView> createPermissions(
      @PathVariable("tenantId") String tenantId, @RequestBody List<PermissionView> permissions) {
    return permissionService.createPermissions(tenantId, permissions);
  }

  @PatchMapping
  public List<PermissionView> updatePermissionNames(
      @PathVariable("tenantId") String tenantId, @RequestBody List<PermissionView> permissions) {
    return permissionService.updatePermissionNames(tenantId, permissions);
  }
}
