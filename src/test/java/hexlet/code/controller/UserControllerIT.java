package hexlet.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import hexlet.code.config.SpringConfigForIT;
import hexlet.code.dtos.UserDto;
import hexlet.code.model.User;
import hexlet.code.repositories.UserRepository;
import hexlet.code.utils.AuthorizationUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static hexlet.code.config.SpringConfigForIT.TEST_PROFILE;
import static hexlet.code.utils.JsonUtils.asJson;
import static hexlet.code.utils.JsonUtils.fromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfigForIT.class)
public class UserControllerIT {

    @Autowired
    private AuthorizationUtils authUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void clear() {
        userRepository.deleteAll();
    }

    private static final String USER_CONTROLLER_PATH =  "/api/users";
    private static final String USER_CONTROLLER_PATH_ID =  "/api/users/{id}";
    private static final String DEFAULT_USER_EMAIL =  "test@gmail.com";

    @Test
    public void addUser() throws Exception {
        assertEquals(0, userRepository.count());
        addDefaultUser().andExpect(status().isCreated());
        assertEquals(1, userRepository.count());
    }

    public ResultActions addDefaultUser() throws Exception {
        final UserDto testUser = getUserDto(DEFAULT_USER_EMAIL, "Test", "Test", "test");
        final MockHttpServletRequestBuilder request = post(USER_CONTROLLER_PATH)
                .content(asJson(testUser))
                .contentType(APPLICATION_JSON);

        return mockMvc.perform(request);
    }

    @Test
    public void getUserById() throws Exception {
        addDefaultUser();

        final User expectedUser = userRepository.findAll().get(0);
        final String email = expectedUser.getEmail();

        final MockHttpServletRequestBuilder request = get(USER_CONTROLLER_PATH_ID, expectedUser.getId());
        authUtils.addToken(request, email);

        final MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final User user = fromJson(response.getContentAsString(), new TypeReference<>() { } );

        assertEquals(expectedUser.getId(), user.getId());
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getFirstName(), user.getFirstName());
        assertEquals(expectedUser.getLastName(), user.getLastName());
    }

    @Test
    public void getAllUsers() throws Exception {
        addDefaultUser();
        final MockHttpServletResponse response = mockMvc.perform(get(USER_CONTROLLER_PATH))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<User> users = fromJson(response.getContentAsString(), new TypeReference<>() { } );

        assertThat(users.size() == 1);
    }

    @Test
    public void updateUser() throws Exception {
        addDefaultUser();

        final User defaultUser = userRepository.findByEmail(DEFAULT_USER_EMAIL).get();
        final Long userId = defaultUser.getId();

        final String updatedEmail = "test2@gmail.com";
        final UserDto userDto = getUserDto(updatedEmail, "Test2", "Test2", "test2");

        final MockHttpServletRequestBuilder updateRequest = put(USER_CONTROLLER_PATH_ID, userId)
                .content(asJson(userDto))
                .contentType(APPLICATION_JSON);

        authUtils.addToken(updateRequest, DEFAULT_USER_EMAIL);

        final MockHttpServletResponse response = mockMvc.perform(updateRequest)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertTrue(userRepository.existsById(userId));
        assertNull(userRepository.findByEmail(DEFAULT_USER_EMAIL).orElse(null));
        assertNotNull(userRepository.findByEmail(updatedEmail).orElse(null));

        final User user = fromJson(response.getContentAsString(), new TypeReference<>() { } );

        assertEquals(1L, user.getId());
        assertEquals(updatedEmail, user.getEmail());
        assertEquals("Test2", user.getFirstName());
        assertEquals("Test2", user.getLastName());
    }

    @Test
    public void deleteUser() throws Exception {
        addDefaultUser();

        final Long userId = userRepository.findByEmail(DEFAULT_USER_EMAIL).get().getId();

        final MockHttpServletRequestBuilder deleteRequest = delete(USER_CONTROLLER_PATH_ID, userId);
        authUtils.addToken(deleteRequest, DEFAULT_USER_EMAIL);

        mockMvc.perform(deleteRequest).andExpect(status().isOk());

        assertEquals(0, userRepository.count());
    }

    private UserDto getUserDto(String email, String firstName, String lastName, String password) {
        return new UserDto(
                email,
                firstName,
                lastName,
                passwordEncoder.encode(password)
        );
    }

}
