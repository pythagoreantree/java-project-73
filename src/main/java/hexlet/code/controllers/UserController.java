package hexlet.code.controllers;

import hexlet.code.dtos.UserDto;
import hexlet.code.model.User;
import hexlet.code.repositories.UserRepository;
import hexlet.code.services.UserServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserServiceImpl userService;

    private final UserRepository userRepository;

    public UserController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    private static final String ONLY_OWNER_BY_ID = """
            @userRepository.findById(#id).get().getEmail().toString() == authentication.getPrincipal().getUsername()
        """;

    @GetMapping("/api/users")
    public List<User> getAll() {
        return userService.findAll();
    }

    @PostMapping("/api/users")
    public void createUser(@RequestBody @Valid final UserDto userDto) {
        userService.createUser(userDto);
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable final Long id) {
        return userService.findUserById(id).get();
    }

    @PutMapping("/api/users/{id}")
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void update(@PathVariable final long id,
                       @RequestBody @Valid final UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/api/users/{id}")
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void delete(@PathVariable final long id) {
        userService.deleteUserById(id);
    }
}
