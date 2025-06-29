package io.formulate.identity.service;

import io.formulate.identity.entity.Role;
import io.formulate.identity.entity.User;
import io.formulate.identity.entity.UserStatus;
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

  public UserView createUser(String tenantId, UserView userView) {
    return userRepository.save(new User(tenantId, userView)).toUserView();
  }

  public void updatePassword(String tenantId, Long userId, boolean activate, String password) {
    User user = userRepository.findById(userId).orElseThrow();
    user.setPassword(password);
    if (activate) {
      user.setStatus(UserStatus.ACTIVE);
    }
    userRepository.save(user);
  }

  public UserView updateRoles(String tenantId, Long userId, List<RoleView> roles) {
    User user = userRepository.findById(userId).orElseThrow();
    user.setRoles(
        roles.stream().map(roleView -> new Role(tenantId, roleView)).collect(Collectors.toList()));
    return userRepository.save(user).toUserView();
  }

  public void updateStatus(String tenantId, Long userId, UserStatus status) {
    User user = userRepository.findById(userId).orElseThrow();
    user.setStatus(status);
    userRepository.save(user);
  }
}
