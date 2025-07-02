package io.formulate.identity.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TenantUserView extends AbstractUserView {
  public static final String TYPE = "Tenant";

  private String password;
  private String firstName;
  private String lastName;
  private UserStatus status;

  public TenantUserView(
      Long id,
      String email,
      String password,
      String username,
      String firstName,
      String lastName,
      UserStatus status,
      List<RoleView> roles,
      List<ProductView> products) {
    super(id, email, username, roles, products);

    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.status = status;
  }
}
