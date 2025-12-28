package com.codeshutlle.project.JWTAndOAuth2.repository;

import com.codeshutlle.project.JWTAndOAuth2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findUserByEmail(String email);


}
