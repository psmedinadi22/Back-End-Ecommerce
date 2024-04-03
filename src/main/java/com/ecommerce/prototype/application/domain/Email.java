package com.ecommerce.prototype.application.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Email {

    private String address;

    public Email(String address) {

        this.address = address;
        validateEmail(address);
    }


    /**
     * Validates whether an email address is valid.
     *
     * @param email The email address to validate.
     * @throws IllegalArgumentException If the email address is null or empty, or if the format is invalid.
     */
    public void validateEmail(String email) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("The user's email cannot be null or empty");
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("The email format is invalid");
        }
    }

    /**
     * Checks if a given string represents a valid email address.
     *
     * @param email The string to be checked as an email address.
     * @return true if the string represents a valid email address, false otherwise.
     */
    private boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

}

