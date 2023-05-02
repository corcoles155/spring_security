package org.sanchez.corcoles.ana.pruebasconcepto.repository;

import org.sanchez.corcoles.ana.pruebasconcepto.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u.username FROM User u")
    List<String> findUsernames();

    @Query("SELECT u.username FROM User u")
    Page<String> findUsernames(Pageable pageable);
}
