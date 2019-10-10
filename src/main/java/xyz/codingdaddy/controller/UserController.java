package xyz.codingdaddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.codingdaddy.domain.User;
import xyz.codingdaddy.repository.UserRepository;

import java.util.List;

/**
 * REST API Controller for {@link xyz.codingdaddy.domain.User} entity
 *
 * @author serhiy
 */
@RestController
@RequestMapping(value = "/api/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@ResponseBody
public class UserController {
 
    @Autowired
    private UserRepository userRepository;

    /**
     * Get user details request handler
     *
     * @param id to retrieve user details
     * @return successful response with user details
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<User> get(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get user list request handler
     *
     * @return the list of users
     */
    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * Create user request handler
     *
     * @param user to be create
     * @return created user with respective id
     */
    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }
}
