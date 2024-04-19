package com.ecommerce.prototype.endpoint;

import com.ecommerce.prototype.application.domain.*;
import com.ecommerce.prototype.application.usecase.*;
import com.ecommerce.prototype.application.usecase.exception.CartNotFoundException;
import com.ecommerce.prototype.application.usecase.exception.InvalidCredentialsException;
import com.ecommerce.prototype.application.usecase.exception.PasswordLengthException;
import com.ecommerce.prototype.application.usecase.exception.UserNoExistException;
import com.ecommerce.prototype.infrastructure.client.mappers.MapperUser;
import com.ecommerce.prototype.infrastructure.client.request.AuthenticationRequest;
import com.ecommerce.prototype.infrastructure.client.request.UserRequest;
import com.ecommerce.prototype.infrastructure.client.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    private final AuthenticateUserUseCase authenticateUserUseCase;
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
                    .withAdmin(user.getIsAdmin())
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


            List<Map<String, Object>> cartsWithUserInfo = carts.stream()
                    .map(cart -> {
                        Map<String, Object> cartInfo = new LinkedHashMap<>();
                        cartInfo.put("cartId", cart.getCartId());
                        cartInfo.put("status", cart.getStatus());
                        cartInfo.put("userId", cart.getUser().getUserId());
                        List<Integer> productIds = cart.getProducts().stream()
                                .map(Product::getProductId)
                                .collect(Collectors.toList());
                        cartInfo.put("productIds", productIds);
                        cartInfo.put("productQuantities", cart.getProductsQuantity());

                        return cartInfo;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(cartsWithUserInfo);
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
            List<Map<String, Object>> ordersWithUserInfo = orders.stream()
                    .map(cart -> {
                        Map<String, Object> orderInfo = new LinkedHashMap<>();
                        orderInfo.put("orderID", cart.getOrderID());
                        orderInfo.put("creationDate", cart.getCreationDate());
                        orderInfo.put("totalAmount", cart.getTotalAmount());
                        orderInfo.put("orderStatus", cart.getOrderStatus());
                        orderInfo.put("userID", userId);
                        return orderInfo;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ordersWithUserInfo);
        } catch (UserNoExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Endpoint to authenticate a user using email and password.
     *
     * @param authenticationRequest The authentication request containing email and password.
     * @return ResponseEntity with an authentication token if successful, or an error message if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            User authenticatedUser = authenticateUserUseCase.authenticateUser(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
