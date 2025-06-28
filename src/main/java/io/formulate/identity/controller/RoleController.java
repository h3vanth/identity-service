package io.formulate.identity.controller;

import io.formulate.identity.model.RoleView;
import io.formulate.identity.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tenants/{tenantId}/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
  private final RoleService roleService;

  @PostMapping
  public List<RoleView> createRoles(
      @PathVariable("tenantId") String tenantId, @RequestBody List<RoleView> roles) {
    return roleService.createRoles(tenantId, roles);
  }

  @PatchMapping
  public List<RoleView> updateRoles(
      @PathVariable("tenantId") String tenantId, @RequestBody List<RoleView> roles) {
    return roleService.updateRoles(tenantId, roles);
  }
}
