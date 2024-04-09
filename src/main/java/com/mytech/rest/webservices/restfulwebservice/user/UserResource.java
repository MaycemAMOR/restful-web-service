package com.mytech.rest.webservices.restfulwebservice.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<User> retrieveUser(@PathVariable int id) { // wrap the User bean by a EntityModel
        User user = service.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id : " + id);
        EntityModel<User> entityModel = EntityModel.of(user); // create the entity model
        // create links
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        //add the link to the entityModel with specific Rel "all-users"
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }


    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
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

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteById(id);
    }
}
