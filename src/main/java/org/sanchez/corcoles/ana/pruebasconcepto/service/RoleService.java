package org.sanchez.corcoles.ana.pruebasconcepto.service;

import lombok.extern.slf4j.Slf4j;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.Role;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.User;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.RoleRepository;
import org.sanchez.corcoles.ana.pruebasconcepto.repository.UserInRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;


    @PostConstruct
    public void init() {
        Arrays.stream(org.sanchez.corcoles.ana.pruebasconcepto.enums.Role.values()).forEach(
                role -> roleRepository.save(new Role(role.getName()))
        );
    }

    public List<Role> getRoles() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Name {}", authentication.getName());
        log.info("Principal {}", authentication.getPrincipal());
        log.info("Credentials {}", authentication.getCredentials());
        log.info("Roles {}", authentication.getAuthorities().toString());
        return roleRepository.findAll();
    }

    public Role get(Integer roleId) {
        final Optional<Role> result = roleRepository.findById(roleId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role with id %d doesn't exist", roleId));
    }

    //@Secured({("ROLE_ADMIN")}) //Sólo podrán acceder a este recurso los usuarios con role ADMIN, también podemos usar @RolesAllowed({"ROLE_ADMIN"}) aunque esta no es una anotación de Spring, pero funciona igual.
    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") //Sólo los usuarios con rol USER y ADMIN podrán ejecutar el método.
    //@PostAuthorize("hasRole('ROLE_ADMIN')") //Todos los roles podrán entrar en el método, pero sólo los usuarios con role ADMIN recibirán una respuesta
    public List<User> getUsersByRole(final String roleName) {
        log.info("Getting users by role name");
        return userInRoleRepository.findUsersByRole(roleName);
    }
}
