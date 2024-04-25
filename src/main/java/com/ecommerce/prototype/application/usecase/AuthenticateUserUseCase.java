package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.domain.Email;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.InvalidCredentialsException;
import com.ecommerce.prototype.application.usecase.exception.OrderNotFoundException;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticateUserUseCase.class);

    /**
     * Authenticates a user based on email and password.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return The authenticated user if credentials are correct.
     * @throws InvalidCredentialsException If email or password is incorrect.
     */
    public User authenticateUser(Email email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Incorrect email or password"));

        if (password.equals(user.getPassword().getValue()))  {
            logger.info("User authenticated successfully with Id: {}", user.getUserId());
            return user;
        } else {
            logger.warn("Incorrect password for user with email: {}", email.getAddress());
            throw new InvalidCredentialsException("Incorrect email or password");
        }
    }
}

