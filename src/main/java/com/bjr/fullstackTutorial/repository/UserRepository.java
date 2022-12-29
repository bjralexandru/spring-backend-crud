package com.bjr.fullstackTutorial.repository;

import com.bjr.fullstackTutorial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
