package com.jobs.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobs.jobportal.model.User;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long>{ 
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);

}
