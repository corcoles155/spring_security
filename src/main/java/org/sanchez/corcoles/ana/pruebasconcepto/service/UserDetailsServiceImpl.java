package org.sanchez.corcoles.ana.pruebasconcepto.service;

import org.sanchez.corcoles.ana.pruebasconcepto.entity.User;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.UserInRole;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.UserInRoleRepository;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            final List<UserInRole> userInRoles = userInRoleRepository.findByUser(user);
            final String[] nameRoles = userInRoles.stream().map(userInRole -> userInRole.getRole().getName()).toArray(String[]::new);
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles(nameRoles).build();
        }
        throw new UsernameNotFoundException("Username " + username + " not found");
    }
}
