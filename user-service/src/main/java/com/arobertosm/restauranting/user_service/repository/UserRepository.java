package com.arobertosm.restauranting.user_service.repository;

import com.arobertosm.restauranting.user_service.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM app_user u WHERE u.username = :username")
    Optional<User> findByUsername(String username);
}
