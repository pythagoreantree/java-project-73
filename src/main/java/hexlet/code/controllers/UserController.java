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
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userService;

    private final UserRepository userRepository;

    public UserController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    private static final String ONLY_OWNER_BY_ID = """
            @userRepository.findById(#id).get().getEmail() == authentication.name
        """;

    @GetMapping
    public List<User> getAll() {
        return userService.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody @Valid final UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable final Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public User update(@PathVariable final long id,
                       @RequestBody @Valid final UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public void delete(@PathVariable final long id) {
        userService.deleteUser(id);
    }
}
