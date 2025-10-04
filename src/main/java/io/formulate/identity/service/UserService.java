package io.formulate.identity.service;

import io.formulate.identity.entity.*;
import io.formulate.identity.model.*;
import io.formulate.identity.repository.tenant.TenantRepository;
import io.formulate.identity.repository.user.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;

  public AbstractUserView createUser(String tenantId, AbstractUserView abstractUserView) {
    Tenant tenant = tenantRepository.findById(Long.valueOf(tenantId)).orElseThrow();
    AbstractUser abstractUser =
        switch (abstractUserView) {
          case TenantUserView userView -> new TenantUser(userView, tenant);
          case IntegrationUserView apiUserView -> new IntegrationUser(apiUserView, tenant);
          default -> null;
        };

    return userRepository.save(abstractUser).toAbstractUserView();
  }

  public void updatePassword(String tenantId, Long userId, boolean activate, String password) {
    AbstractUser abstractUser = userRepository.findById(userId).orElseThrow();

    if (!(abstractUser instanceof TenantUser)) {
      throw new RuntimeException("Invalid type!");
    }

    TenantUser tenantUser = (TenantUser) abstractUser;
    tenantUser.setPassword(password);
    if (activate) {
      tenantUser.setStatus(UserStatus.ACTIVE);
    }

    userRepository.save(tenantUser);
  }

  public AbstractUserView updateRoles(String tenantId, Long userId, List<RoleView> roles) {
    AbstractUser user = userRepository.findById(userId).orElseThrow();
    Tenant tenant = tenantRepository.findById(Long.valueOf(tenantId)).orElseThrow();
    user.setRoles(
        roles.stream().map(roleView -> new Role(roleView, tenant)).collect(Collectors.toList()));
    return userRepository.save(user).toAbstractUserView();
  }

  public List<AbstractUserView> getUsers(String tenantId) {
    return userRepository.findAllByTenant_Id(Long.valueOf(tenantId)).stream()
        .map(AbstractUser::toAbstractUserView)
        .toList();
  }

  // Only applicable for io.formulate.identity.entity.TenantUser
  public void updateStatus(String tenantId, Long userId, UserStatus status) {
    TenantUser tenantUser = (TenantUser) userRepository.findById(userId).orElseThrow();
    tenantUser.setStatus(status);
    userRepository.save(tenantUser);
  }
}
