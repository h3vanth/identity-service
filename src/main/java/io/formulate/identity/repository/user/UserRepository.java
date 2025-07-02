package io.formulate.identity.repository.user;

import io.formulate.identity.entity.AbstractUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AbstractUser, Long> {
  List<AbstractUser> findAllByTenantId(String tenantId);
}
