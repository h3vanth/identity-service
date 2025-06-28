package io.formulate.identity.controller;

import io.formulate.identity.model.RoleView;
import io.formulate.identity.model.UserView;
import io.formulate.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenants/{tenantId}/api/v1/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping
  public UserView createUser(
      @PathVariable("tenantId") String tenantId, @RequestBody UserView userView) {
    return userService.createUser(tenantId, userView);
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
  public UserView updateRoles(
      @PathVariable("tenantId") String tenantId,
      @PathVariable("userId") Long userId,
      @RequestBody List<RoleView> roles) {
    return userService.updateRoles(tenantId, userId, roles);
  }
}
