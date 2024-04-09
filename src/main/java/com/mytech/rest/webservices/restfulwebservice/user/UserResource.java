package com.mytech.rest.webservices.restfulwebservice.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {


    private final UserDaoService service;


    //dependency Injection in constrictor
    public UserResource(UserDaoService userDaoService) {
        this.service = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id : " + id);

        return user;
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.saveUser(user);
        // envoyer location aprés la création de chaque user
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build(); /* amiliorer le code status de retour sucess response => 200
                                                            created status => 201
                                                            no content => 204
                                                            unauthorized (when authorization fails) => 401
                                                            Bad Request (validation error) => 400
                                                            resourcer is not found => 404
                                                            server exception => 500*/
    }
}
