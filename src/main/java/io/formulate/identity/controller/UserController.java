package io.formulate.identity.controller;

import io.formulate.identity.model.AbstractUserView;
import io.formulate.identity.model.RoleView;
import io.formulate.identity.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tenants/{tenantId}/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping
  public AbstractUserView createUser(
      @PathVariable("tenantId") String tenantId, @RequestBody AbstractUserView abstractUserView) {
    return userService.createUser(tenantId, abstractUserView);
  }

  @PatchMapping(value = "/{userId}/password", consumes = MediaType.TEXT_PLAIN_VALUE)
  public void updatePassword(
      @PathVariable("tenantId") String tenantId,
      @PathVariable("userId") Long userId,
      @RequestParam(value = "activate", defaultValue = "false") boolean activate,
      @RequestBody String password) {
    userService.updatePassword(tenantId, userId, activate, password);
  }

  @PatchMapping("/{userId}/roles")
  public AbstractUserView updateRoles(
      @PathVariable("tenantId") String tenantId,
      @PathVariable("userId") Long userId,
      @RequestBody List<RoleView> roles) {
    return userService.updateRoles(tenantId, userId, roles);
  }

  @GetMapping
  public List<AbstractUserView> getUsers(@PathVariable("tenantId") String tenantId) {
    return userService.getUsers(tenantId);
  }

  // Create endpoints to update roles, products in bulk
}
