package xyz.codingdaddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.codingdaddy.domain.User;
import xyz.codingdaddy.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@ResponseBody
public class UserController {
 
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> get(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/users")
    public List<User> list() {
        return userRepository.findAll();
    }
}