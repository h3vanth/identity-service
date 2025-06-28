package io.formulate.identity.repository.user;

import io.formulate.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {}

interface CustomUserRepository {}
