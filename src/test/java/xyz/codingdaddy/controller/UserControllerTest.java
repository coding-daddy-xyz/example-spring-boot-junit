package xyz.codingdaddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import xyz.codingdaddy.domain.User;
import xyz.codingdaddy.repository.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link UserController}
 *
 * @author serhiy
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        userRepository.save(User.of("bob", "1234", "bob@example.com"));
        userRepository.save(User.of("alice", "4321", "alice@example.com"));
        userRepository.save(User.of("gordon", "hl3-confirmed", "gordon@example.com"));
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @Profile("test")
    public void testGetExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("bob"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.email").value("bob@example.com"));

    }

    @Test
    public void testGetNonExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1001"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].username").value("bob"))
                .andExpect(jsonPath("$[1].username").value("alice"))
                .andExpect(jsonPath("$[2].username").value("gordon"));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("john");
        user.setPassword("7777");
        user.setEmail("john@example.com");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.password").value("7777"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}