package com.example.learntocode.repository;


import com.example.learntocode.models.User;
import com.example.learntocode.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByAuth0Id(String auth0Id);

    Optional<List<User>> findAllByStatus(Status status);

    @Query("SELECT u.username FROM User u WHERE u.id IN :ids")
    List<String> findUsernamesByIdIn(@Param("ids") Set<Long> ids);

    boolean existsByEmail(String email);
}
