package com.ecommerce.prototype.infrastructure.persistence.provider;

import com.ecommerce.prototype.application.domain.Buyer;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.application.usecase.repository.UserRepository;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.persistence.modeldb.Userdb;
import com.ecommerce.prototype.infrastructure.persistence.provider.jparepository.UserJPARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserProvider implements UserRepository {

    private final UserJPARepository userJPARepository;

    /**
     * Saves a user to the database.
     *
     * @param user The user to be saved.
     * @return An optional containing the saved user if successful, otherwise empty.
     */
    @Override
    public Optional<User> save(User user) {

       Userdb userdb = MapperUser.toUserModel(user);
       return Optional.ofNullable(MapperUser.toUserDomain(userJPARepository.save(userdb)));
    }

    /**
     * Checks if a user exists with the given email.
     *
     * @param email The email to check.
     * @return True if a user exists with the given email, otherwise false.
     */
    @Override
    public boolean existByEmail(String email) {
        return userJPARepository.existsByEmail(email);
    }

    /**
     * Checks if a user exists with the given ID.
     *
     * @param userId The ID of the user to check.
     * @return True if a user exists with the given ID, otherwise false.
     */
    @Override
    public boolean existsById(Integer userId) {

        return userJPARepository.existsById(userId);
    }

    /**
     * Deletes a user by ID.
     *
     * @param userId The ID of the user to delete.
     */
    @Override
    public void deleteById(Integer userId) {

        Userdb user = userJPARepository.findById(userId)
                .orElseThrow(() -> new UserNoExistException("User not found with ID: " + userId));
        user.setIsDeleted(true);
        userJPARepository.save(user);
    }

    /**
     * Retrieves a user by ID.
     *
     * @return The user if found, otherwise null.
     */
    @Override
    public Buyer findBuyerById(Integer buyerId) {

        return userJPARepository.findByUserId(buyerId)
                                .toBuyer();
    }

    @Override
    public User findUserById(Integer userId) {

        return MapperUser.toUserDomain(userJPARepository.findByUserId(userId));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(MapperUser.toUserDomain(userJPARepository.findByEmail(email)));
    }
}


