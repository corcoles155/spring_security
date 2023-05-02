package org.sanchez.corcoles.ana.pruebasconcepto.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.Role;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.User;
import org.sanchez.corcoles.ana.pruebasconcepto.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @ApiOperation(value = "get roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(service.getRoles(), HttpStatus.OK);
    }


    @ApiOperation(value = "get roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GetMapping("{roleName}/users")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable("roleName") String roleName) {
        return new ResponseEntity<>(service.getUsersByRole(roleName), HttpStatus.OK);
    }

    @ApiOperation(value = "get role by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GetMapping("/{roleId}")
    public ResponseEntity<Role> get(@PathVariable("roleId") Integer roleId) {
        return new ResponseEntity<>(service.get(roleId), HttpStatus.OK);
    }

}
