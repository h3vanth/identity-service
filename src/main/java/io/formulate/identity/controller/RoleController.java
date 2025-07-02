package io.formulate.identity.controller;

import io.formulate.identity.model.RoleView;
import io.formulate.identity.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tenants/{tenantId}/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
  private final RoleService roleService;

  @PostMapping
  public List<RoleView> upsertRoles(
      @PathVariable("tenantId") String tenantId, @RequestBody List<RoleView> roles) {
    return roleService.upsertRoles(tenantId, roles);
  }

  @DeleteMapping
  public void deleteRoles(
      @PathVariable("tenantId") String tenantId, @RequestBody List<Long> roleIds) {
    roleService.deleteRoles(tenantId, roleIds);
  }
}
