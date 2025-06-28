package io.formulate.identity.repository.role;

import io.formulate.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, CustomRoleRepository {}

interface CustomRoleRepository {}
