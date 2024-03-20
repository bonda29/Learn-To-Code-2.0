package com.example.learntocode.repository;


import com.example.learntocode.models.User;
import com.example.learntocode.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByAuth0Id(String auth0Id);

    Optional<List<User>> findAllByStatus(Status status);

    boolean existsByEmail(String email);
}
