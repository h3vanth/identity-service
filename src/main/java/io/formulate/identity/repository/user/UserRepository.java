package io.formulate.identity.repository.user;

import io.formulate.identity.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BaseUser, Long>, CustomUserRepository {}

interface CustomUserRepository {}
