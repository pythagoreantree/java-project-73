package hexlet.code.controllers;

import hexlet.code.dtos.UserDto;
import hexlet.code.model.User;
import hexlet.code.services.UserService;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public void update(@PathVariable final long id,
                       @RequestBody @Valid final UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/api/users/{id}")
    public void delete(@PathVariable final long id) {
        userService.deleteUserById(id);
    }
}
