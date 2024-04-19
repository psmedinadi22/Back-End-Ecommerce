package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.UserAlreadyExistException;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class CreateUserUseCase {

    private UserRepository userRepository;

    /**
     * Creates a new user.
     *
     * @param user The user to create.
     * @return An optional containing the created user if successful, empty otherwise.
     * @throws UserAlreadyExistException If a user with the same email address already exists.
     */
    public Optional<User> createUser(User user) {

        if (userRepository.existByEmail(user.getEmail().getAddress())) {
            throw new UserAlreadyExistException("The user with the email already exists: " + user.getEmail().getAddress());
        }
        user.setIsDeleted(false);
        return userRepository.save(user);
    }
}
