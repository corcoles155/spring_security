package org.sanchez.corcoles.ana.pruebasconcepto.service;

import org.sanchez.corcoles.ana.pruebasconcepto.entity.Role;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.User;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.RoleRepository;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.UserInRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;


    @PostConstruct
    public void init() {
        final List<Role> roles = Arrays.asList(new Role("ADMIN"), new Role("SUPPORT"), new Role("USER"));
        for (Role role : roles) {
            roleRepository.save(role);
        }
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role get(Integer roleId) {
        final Optional<Role> result = roleRepository.findById(roleId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role with id %d doesn't exist", roleId));
    }

    public List<User> getUsersByRole(final String roleName) {
        return userInRoleRepository.findUsersByRole(roleName);
    }
}
