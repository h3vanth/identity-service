package io.formulate.identity.service;

import io.formulate.identity.entity.*;
import io.formulate.identity.model.TenantView;
import io.formulate.identity.repository.permission.PermissionRepository;
import io.formulate.identity.repository.product.ProductRepository;
import io.formulate.identity.repository.property.PropertyRepository;
import io.formulate.identity.repository.role.RoleRepository;
import io.formulate.identity.repository.tenant.TenantRepository;
import io.formulate.identity.repository.user.UserRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TenantService {
  // Ideally, we should interact with the service
  private final PermissionRepository permissionRepository;
  private final ProductRepository productRepository;
  private final PropertyRepository propertyRepository;
  private final RoleRepository roleRepository;
  private final TenantRepository tenantRepository;
  private final UserRepository userRepository;

  public List<TenantView> getTenants() {
    return Tenant.toTenantViews(tenantRepository.findAll());
  }

  public TenantView getTenant(String tenantId) {
    return tenantRepository.findById(Long.valueOf(tenantId)).orElseThrow().toTenantView();
  }

  public List<TenantView> upsertTenants(List<TenantView> tenantViews) {
    List<Tenant> tenants =
        tenantRepository.saveAll(
            tenantViews.stream().map(Tenant::new).collect(Collectors.toList()));
    return Tenant.toTenantViews(tenants);
  }

  @Transactional
  public void deleteTenants(Set<Long> tenantIds) {
    List<Tenant> tenants = tenantRepository.findAllById(tenantIds);

    for (Tenant tenant : tenants) {
      Long tenantId = tenant.getId();

      productRepository.deleteAllByTenantId(tenantId);
      permissionRepository.deleteAllByTenantId(tenantId);
      roleRepository.deleteAllByTenantId(tenantId);
      userRepository.deleteAllByTenantId(tenantId);
      propertyRepository.deleteAllByTenantId(tenantId);
    }

    tenantRepository.deleteAll(tenants);
  }
}
