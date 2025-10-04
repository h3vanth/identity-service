package io.formulate.identity.controller;

import io.formulate.identity.model.TenantView;
import io.formulate.identity.service.TenantService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {
  private final TenantService tenantService;

  @GetMapping
  public List<TenantView> getTenants() {
    return tenantService.getTenants();
  }

  @PostMapping
  public List<TenantView> upsertTenants(@RequestBody List<TenantView> tenantViews) {
    return tenantService.upsertTenants(tenantViews);
  }

  @DeleteMapping
  public void deleteTenants(@RequestBody Set<Long> tenantIds) {
    tenantService.deleteTenants(tenantIds);
  }
}
