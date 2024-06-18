package com.example.login_auth_api.repositories;

import com.example.login_auth_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
