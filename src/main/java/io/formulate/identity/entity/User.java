package io.formulate.identity.entity;

import io.formulate.identity.model.BaseUserView;
import io.formulate.identity.model.UserView;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseUser {
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

  public User(String tenantId, UserView userView) {
    super(tenantId, userView);

    password = userView.getPassword();
    firstName = userView.getFirstName();
    lastName = userView.getLastName();
  }

  @PrePersist
  public void prePersist() {
    status = UserStatus.INACTIVE;
    // Hash password
  }

  @PreUpdate
  public void preUpdate() {}

  @Override
  public BaseUserView toBaseUserView() {
    return new UserView(
        id, email, null, username, firstName, lastName, status, Role.toRoleViews(roles));
  }
}
