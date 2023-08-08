package com.techiesmeetups.web.repository;

import com.techiesmeetups.web.models.Role;
import com.techiesmeetups.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserName(String userName);

    UserEntity findFirstByUserName(String username);
}
