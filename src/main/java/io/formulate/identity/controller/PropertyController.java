package io.formulate.identity.controller;

import io.formulate.identity.model.PropertyView;
import io.formulate.identity.service.PropertyService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/tenants/{tenantId}/properties")
@RequiredArgsConstructor
public class PropertyController {
  private final PropertyService propertyService;

  @GetMapping
  public List<PropertyView> getProperties(@PathVariable("tenantId") String tenantId) {
    return propertyService.getProperties(tenantId);
  }

  @PostMapping
  public List<PropertyView> upsertProperties(
      @PathVariable("tenantId") String tenantId, @RequestBody List<PropertyView> propertyViews) {
    return propertyService.upsertProperties(tenantId, propertyViews);
  }

  @DeleteMapping
  public void deleteProperties(
      @PathVariable("tenantId") String tenantId, @RequestBody Set<Long> propertyIds) {
    propertyService.deleteProperties(tenantId, propertyIds);
  }
}
