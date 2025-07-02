package io.formulate.identity.model;

import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IntegrationUserView extends AbstractUserView {
  public static final String TYPE = "Integration";

  public IntegrationUserView(
      Long id, String email, String username, List<RoleView> roles, List<ProductView> products) {
    super(id, email, username, roles, products);
  }
}
