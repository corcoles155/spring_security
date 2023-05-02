package org.sanchez.corcoles.ana.pruebasconcepto.repository;

import org.sanchez.corcoles.ana.pruebasconcepto.entity.User;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.UserInRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInRoleRepository extends JpaRepository<UserInRole, Integer> {

    @Query("SELECT u.user FROM UserInRole u WHERE u.role.name=?1")
    List<User> findUsersByRole(String roleName);

    List<UserInRole> findByUser(User user);
}
