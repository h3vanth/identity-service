package io.formulate.identity.entity;

import io.formulate.identity.model.AbstractUserView;
import io.formulate.identity.model.IntegrationUserView;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "integration_users")
@DiscriminatorValue("INTEGRATION")
@Getter
@Setter
@NoArgsConstructor
public class IntegrationUser extends AbstractUser {
  @Column(name = "api_key", updatable = false, nullable = true)
  private String apiKey;

  public IntegrationUser(String tenantId, IntegrationUserView integrationUserView) {
    super(tenantId, integrationUserView);
  }

  @Override
  public AbstractUserView toAbstractUserView() {
    return new IntegrationUserView(
        id, email, username, Role.toRoleViews(roles), Product.toProductViews(products));
  }
}
