package com.ecommerce.prototype.endpoint;


import com.ecommerce.prototype.application.domain.Cart;
import com.ecommerce.prototype.application.domain.Order;
import com.ecommerce.prototype.application.domain.TokenizedCard;
import com.ecommerce.prototype.application.domain.User;
import com.ecommerce.prototype.application.usecase.*;
import com.ecommerce.prototype.application.usecase.exception.CartNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.PasswordLengthException;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.client.request.UserRequest;
import com.ecommerce.prototype.infrastructure.client.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@AllArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserCartsUseCase getUserCartsUseCase;
    private final GetUserTokenizedCartsUseCase getUserTokenizedCartsUseCase;
    private final GetUserOrdersUseCase getUserOrdersUseCase;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /**
     * Creates a new user.
     *
     * @param userRequest The request containing user information.
     * @return ResponseEntity<UserResponse> A response entity containing the created user if successful,
     * or an error response if an exception occurs.
     */
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {

        UserResponse.UserResponseBuilder userBuilder = UserResponse.builder();
        try {
            logger.info("User: {}", userRequest);
            User mapperUser = MapperUser.toUserDomain(userRequest);
            logger.info("Mapped User: {}", mapperUser);

            User user = createUserUseCase.createUser(mapperUser)
                    .orElseThrow(() -> new RuntimeException("Error saving user"));

            userBuilder.withUserId(user.getUserId())
                    .withName(user.getName())
                    .withEmail(user.getEmail())
                    .withPassword(user.getPassword())
                    .withPhoneNumber(user.getPhoneNumber())
                    .withIdentificationType(user.getIdentificationType())
                    .withIdentificationNumber(user.getIdentificationNumber())
                    .withAdmin(user.getAdmin())
                    .withShippingAddress(user.getShippingAddress())
                    .withBillingAddress(user.getBillingAddress());

            return new ResponseEntity<>(userBuilder.build(), HttpStatus.CREATED);
        } catch (IllegalArgumentException | PasswordLengthException e) {

            userBuilder.withError(e.getMessage());

            return new ResponseEntity<>(userBuilder.build(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {

            userBuilder.withError(e.getMessage());

            return new ResponseEntity<>(userBuilder.build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a user.
     *
     * @param userId The ID of the user to delete.
     * @return ResponseEntity<UserResponse> A response entity indicating success or failure of the operation.
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {

        try {
            deleteUserUseCase.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } catch (UserNoExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found error: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves the carts of a user.
     *
     * @param userId The ID of the user whose carts are retrieved.
     * @return ResponseEntity<List<Cart>> A response entity containing the list of carts if successful,
     * or an internal server error response if an exception occurs.
     */
    @GetMapping("/user/{userId}/carts")
    public ResponseEntity<?> getUserCarts(@PathVariable Integer userId) {
        try {
            List<Cart> carts = getUserCartsUseCase.getUserCarts(userId);
            return ResponseEntity.ok(carts);
        } catch (UserNoExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/tokenizedCads")
    public ResponseEntity<?> getUserTokenizedCarts(@PathVariable Integer userId) {
        try {
            List<TokenizedCard> tokenizedCards = getUserTokenizedCartsUseCase.getUserTokenizedCarts(userId);
            return ResponseEntity.ok(tokenizedCards);
        } catch (UserNoExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves orders for a specific user.
     * @param userId ID of the user.
     * @return ResponseEntity with the list of orders belonging to the user.
     */
    @GetMapping("/user/{userId}/orders")
    public ResponseEntity<?> getUserOrders(@PathVariable Integer userId) {
        try {
            List<Order> orders = getUserOrdersUseCase.getUserOrders(userId);
            return ResponseEntity.ok(orders);
        } catch (UserNoExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
