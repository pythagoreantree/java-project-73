package hexlet.code.services;

import hexlet.code.dtos.UserDto;
import hexlet.code.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    List<User> findAll();

    User findUserById(Long id);

    User findUserByEmail(String email);

    User createUser(UserDto userDto);

    User updateUser(long id, UserDto userDto);

    void deleteUser(long id);
}
