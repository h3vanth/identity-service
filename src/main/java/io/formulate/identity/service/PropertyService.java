package io.formulate.identity.service;

import io.formulate.identity.entity.Property;
import io.formulate.identity.entity.Tenant;
import io.formulate.identity.model.PropertyView;
import io.formulate.identity.repository.property.PropertyRepository;
import io.formulate.identity.repository.tenant.TenantRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PropertyService {
  private final PropertyRepository propertyRepository;
  private final TenantRepository tenantRepository;

  public List<PropertyView> getProperties(String tenantId) {
    return Property.toPropertyViews(propertyRepository.findAllByTenant_Id(Long.valueOf(tenantId)));
  }

  public List<PropertyView> upsertProperties(String tenantId, List<PropertyView> propertyViews) {
    Tenant tenant = tenantRepository.findById(Long.valueOf(tenantId)).orElseThrow();
    List<Property> properties =
        propertyRepository.saveAll(
            propertyViews.stream()
                .map(propertyView -> new Property(propertyView, tenant))
                .collect(Collectors.toList()));
    return Property.toPropertyViews(properties);
  }

  @Transactional
  public void deleteProperties(String tenantId, Set<Long> propertyIds) {
    Tenant tenant = tenantRepository.findById(Long.valueOf(tenantId)).orElseThrow();
    List<Property> properties = propertyRepository.findAllById(propertyIds);
    if (tenant.getProperties() != null) {
      tenant.getProperties().removeAll(properties);
    }
    propertyRepository.deleteAll(properties);
  }
}
