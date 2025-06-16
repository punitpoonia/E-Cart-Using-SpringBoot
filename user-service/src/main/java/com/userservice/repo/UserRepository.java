package com.userservice.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
	User findByPassword(String password);
	User findByUsernameAndPassword(String username, String password);


}