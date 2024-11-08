package redsocial.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>)userRepository.findAll();
    }   

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UserNotFoundException(id);
        return user;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        return userRepository.findById(id)
            .map(existingUser -> {
                // Actualizar solo los campos no nulos
                if (user.getUsername() != null) {
                    existingUser.setUsername(user.getUsername());
                }
                if (user.getEmail() != null) {
                    existingUser.setEmail(user.getEmail());
                }
                if (user.getPassword() != null) {
                    existingUser.setPassword(user.getPassword());
                }
                // Guardar los cambios
                return userRepository.save(existingUser);
            })
            .orElse(null);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }

}
