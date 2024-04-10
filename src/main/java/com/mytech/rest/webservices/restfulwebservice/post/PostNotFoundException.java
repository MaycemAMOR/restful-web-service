package com.mytech.rest.webservices.restfulwebservice.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
// @ResponseStatus(code = HttpStatus.NOT_FOUND)  => pour bien spécifier le code de status NotFound '404
// de retour aprés chaque request quand la resorce n'esiste pas
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
