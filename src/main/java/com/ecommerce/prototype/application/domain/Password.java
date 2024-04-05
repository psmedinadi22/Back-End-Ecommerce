package com.ecommerce.prototype.application.domain;

import com.ecommerce.prototype.application.usecase.exception.PasswordLengthException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Password {

    private String value;

    public Password(String password){

        this.value = password;
        validatePassword(password);
    }

    /**
     * Validates the provided password to ensure it meets the minimum length requirement.
     *
     * @param password The password to validate.
     * @throws PasswordLengthException If the provided password is null or less than 8 characters long.
     */
    public void validatePassword(String password){

        if (password == null || password.length() < 8) {
            throw new PasswordLengthException("Password must be at least 8 characters");
        }
    }
}
