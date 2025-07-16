package com.arobertosm.restauranting.user_service.repository;

import com.arobertosm.restauranting.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
