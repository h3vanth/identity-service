package io.formulate.identity.model;

import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiUserView extends BaseUserView {
  public static final String TYPE = "ApiUserView";

  public ApiUserView(Long id, String email, String username, List<RoleView> roles) {
    super(id, email, username, roles);
  }
}
