package io.formulate.identity.repository.user;

import io.formulate.identity.entity.AbstractUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<AbstractUser, Long> {
  List<AbstractUser> findAllByTenant_Id(Long tenantId);

  @Modifying
  @Query(value = "delete from users where tenant_id = :tenantId", nativeQuery = true)
  void deleteAllByTenantId(@Param("tenantId") Long tenantId);
}
