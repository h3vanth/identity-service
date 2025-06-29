package io.formulate.identity.entity;

import io.formulate.identity.model.ApiUserView;
import io.formulate.identity.model.BaseUserView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_users")
@Getter
@Setter
@NoArgsConstructor
public class ApiUser extends BaseUser {
  @Column(name = "api_key", updatable = false, nullable = true)
  private String apiKey;

  public ApiUser(String tenantId, ApiUserView apiUserView) {
    super(tenantId, apiUserView);
  }

  @Override
  public BaseUserView toBaseUserView() {
    return new ApiUserView(id, email, username, Role.toRoleViews(roles));
  }
}
