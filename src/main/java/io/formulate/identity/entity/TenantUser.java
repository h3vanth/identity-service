package io.formulate.identity.entity;

import io.formulate.identity.model.AbstractUserView;
import io.formulate.identity.model.UserStatus;
import io.formulate.identity.model.TenantUserView;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "tenant_users")
@DiscriminatorValue("TENANT")
@Getter
@Setter
@NoArgsConstructor
public class TenantUser extends AbstractUser {
  @Column(nullable = false)
  private String password;

  @Column(name = "first_name", updatable = false, nullable = false)
  private String firstName;

  @Column(name = "last_name", updatable = false, nullable = false)
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updateTime;

  public TenantUser(String tenantId, TenantUserView tenantUserView) {
    super(tenantId, tenantUserView);

    password = tenantUserView.getPassword();
    firstName = tenantUserView.getFirstName();
    lastName = tenantUserView.getLastName();
  }

  @PrePersist
  public void prePersist() {
    status = UserStatus.INACTIVE;
    // Hash password
  }

  @PreUpdate
  public void preUpdate() {}

  @Override
  public AbstractUserView toAbstractUserView() {
    return new TenantUserView(
        id,
        email,
        null,
        username,
        firstName,
        lastName,
        status,
        Role.toRoleViews(roles),
        Product.toProductViews(products));
  }
}
