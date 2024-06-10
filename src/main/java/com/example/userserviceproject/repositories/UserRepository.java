package com.example.userserviceproject.repositories;

import com.example.userserviceproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    public User save(User user);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findUserByEmail(String email);

}
