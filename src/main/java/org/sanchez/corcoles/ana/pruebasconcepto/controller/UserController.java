package org.sanchez.corcoles.ana.pruebasconcepto.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sanchez.corcoles.ana.pruebasconcepto.entity.User;
import org.sanchez.corcoles.ana.pruebasconcepto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "get users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                               @RequestParam(name = "size", required = false, defaultValue = "20") int size) {
        return new ResponseEntity<>(service.getUsers(page, size), HttpStatus.OK);
    }

    @ApiOperation(value = "get user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<User> get(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(service.get(userId), HttpStatus.OK);
    }

    @ApiOperation(value = "get user by username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<User> get(@PathVariable("username") String username) {
        return new ResponseEntity<>(service.getByUsername(username), HttpStatus.OK);
    }

}
