package com.ecommerce.prototype.application.usecase;

import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCase {
    private UserRepository userRepository;

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     * @throws UserNoExistException If the user with the given ID does not exist.
     */
    public void deleteUser(Integer userId) {

        userRepository.deleteById(userId);
    }
}
