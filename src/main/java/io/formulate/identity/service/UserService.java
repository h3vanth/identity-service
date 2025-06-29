package io.formulate.identity.service;

import io.formulate.identity.entity.*;
import io.formulate.identity.model.ApiUserView;
import io.formulate.identity.model.BaseUserView;
import io.formulate.identity.model.RoleView;
import io.formulate.identity.model.UserView;
import io.formulate.identity.repository.user.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public BaseUserView createUser(String tenantId, BaseUserView baseUserView) {
    BaseUser user = null;
    if (baseUserView instanceof UserView userView) {
      user = new User(tenantId, userView);
    } else if (baseUserView instanceof ApiUserView apiUserView) {
      user = new ApiUser(tenantId, apiUserView);
    }

    if (user == null) {
      throw new RuntimeException("Invalid type!");
    }

    return userRepository.save(user).toBaseUserView();
  }

  public void updatePassword(String tenantId, Long userId, boolean activate, String password) {
    BaseUser baseUser = userRepository.findById(userId).orElseThrow();

    if (!(baseUser instanceof User)) {
      throw new RuntimeException("Invalid type!");
    }

    User user = (User) baseUser;
    user.setPassword(password);
    if (activate) {
      user.setStatus(UserStatus.ACTIVE);
    }

    userRepository.save(user);
  }

  public BaseUserView updateRoles(String tenantId, Long userId, List<RoleView> roles) {
    BaseUser user = userRepository.findById(userId).orElseThrow();
    user.setRoles(
        roles.stream().map(roleView -> new Role(tenantId, roleView)).collect(Collectors.toList()));
    return userRepository.save(user).toBaseUserView();
  }

  // Only applicable for io.formulate.identity.entity.User
  public void updateStatus(String tenantId, Long userId, UserStatus status) {
    User user = (User) userRepository.findById(userId).orElseThrow();
    user.setStatus(status);
    userRepository.save(user);
  }
}
