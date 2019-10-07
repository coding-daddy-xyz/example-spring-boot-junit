package xyz.codingdaddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.codingdaddy.repository.UserRepository;

/**
 * JUnit String Boot application example
 *
 * @author serhiy
 */
@SpringBootApplication
public class Application {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}