package com.itoshi_m_dev.schedulingapi.repositories;

import com.itoshi_m_dev.schedulingapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByLogin(String login);
}
