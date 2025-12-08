package com.dbteam7.backend.user.repository;

import com.dbteam7.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByMail(String mail);
    boolean existsByMail(String mail);
}

