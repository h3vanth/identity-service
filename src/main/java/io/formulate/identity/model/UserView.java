package io.formulate.identity.model;

import io.formulate.identity.entity.UserStatus;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserView extends BaseUserView {
  public static final String TYPE = "UserView";

  private String password;
  private String firstName;
  private String lastName;
  private UserStatus status;

  public UserView(
      Long id,
      String email,
      String password,
      String username,
      String firstName,
      String lastName,
      UserStatus status,
      List<RoleView> roles) {
    super(id, email, username, roles);

    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.status = status;
  }
}
