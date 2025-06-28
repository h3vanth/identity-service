package io.formulate.identity.model;

import io.formulate.identity.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserView {
  private Long id;
  private String email;
  private String password;
  private String username;
  private String firstName;
  private String lastName;
  private UserStatus status;
  private List<RoleView> roles;
}
