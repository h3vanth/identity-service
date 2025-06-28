package io.formulate.identity.entity;

import io.formulate.identity.model.UserView;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(updatable = false, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(updatable = false, nullable = false, unique = true)
  private String username;

  @Column(name = "first_name", updatable = false, nullable = false)
  private String firstName;

  @Column(name = "last_name", updatable = false, nullable = false)
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status;

  @Version
  @Column(nullable = false)
  private Long version;

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime createTime;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updateTime;

  @Column(name = "tenant_id", updatable = false, nullable = false)
  private String tenantId;

  @OneToMany private List<Role> roles;

  public User(String tenantId, UserView userView) {
    id = userView.getId();
    email = userView.getEmail();
    password = userView.getPassword();
    username = userView.getUsername();
    firstName = userView.getFirstName();
    lastName = userView.getLastName();

    this.tenantId = tenantId;
  }

  @PrePersist
  public void prePersist() {
    status = UserStatus.INACTIVE;
    // Hash password
  }

  @PreUpdate
  public void preUpdate() {}

  public UserView toUserView() {
    return new UserView(
        id, email, null, username, firstName, lastName, status, Role.toRoleViews(roles));
  }
}
