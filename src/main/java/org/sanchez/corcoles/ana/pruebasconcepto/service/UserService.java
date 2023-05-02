package org.sanchez.corcoles.ana.pruebasconcepto.service;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.Role;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.User;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.UserInRole;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.RoleRepository;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.UserInRoleRepository;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private Faker faker;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;

    @PostConstruct
    public void init() {
        final List<Role> roles = roleRepository.findAll();
        for (int i = 0; i <= 10; i++) {
            final User user = userRepository.save(new User(faker.name().username(), faker.artist().name()));
            final Integer roleId = getRandomNumber(1, 4);
            final Optional<Role> role = roleRepository.findById(roleId);
            final UserInRole userInRole = userInRoleRepository.save(new UserInRole(user, role.get()));
            log.info("UserName {} password {} role {}", user.getUsername(), user.getPassword(), role.get().getName());
        }
    }

    public Page<User> getUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }


    public User get(Integer userId) {
        return userRepository.findById(userId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d doesn't exist", userId)));
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with username %s doesn't exist", username)));
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
