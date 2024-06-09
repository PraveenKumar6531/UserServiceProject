package com.example.userserviceproject.repositories;

import com.example.userserviceproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    public User save(User user);

}
