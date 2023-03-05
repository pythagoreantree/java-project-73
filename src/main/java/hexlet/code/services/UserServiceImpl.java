package hexlet.code.services;

import hexlet.code.dtos.UserDto;
import hexlet.code.model.Role;
import hexlet.code.model.User;
import hexlet.code.dtos.UserDetailsImpl;
import hexlet.code.repositories.RoleRepository;
import hexlet.code.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    private static final String ROLE_USER = "USER";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .toList();
    }

    public void createUser(UserDto userDto) {
        final User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        initRoles(user);
        userRepository.save(user);
    }

    private void initRoles(User user) {
        List<Role> roles = user.getRoles();
        if (roles == null) roles = new ArrayList<>();
        roles.add(getUserRole());
        user.setRoles(roles);
    }

    private Role getUserRole() {
        Optional<Role> role = roleRepository.findByName(ROLE_USER);
        return role.get();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public void updateUser(long id, UserDto userDto) {
        final User user = userRepository.findById(id).get();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
        return new UserDetailsImpl(user.get());
    }
}
