/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.Objets;

import java.util.Objects;

/**
 * Represents a user in the system with personal details and credentials.
 * <p>
 * This class provides a blueprint for creating and managing user objects, including their personal
 * information and authentication details.
 * </p>
 *
 * @author DevDoctor
 * @version 1.0
 */
public class User {
    /**
     * The user's first name.
     */
    private String name;

    /**
     * The user's last name.
     */
    private String lastname;

    /**
     * The user's fiscal code (e.g., tax identification number).
     */
    private String fiscal_code;

    /**
     * The user's email address.
     */
    private String email;

    /**
     * The user's hashed password.
     */
    private String password;

    /**
     * The user's unique identifier.
     */
    private Integer id;

    /**
     * Default constructor for creating an empty {@code User} object.
     * <p>
     * Initializes all fields to {@code null}.
     * </p>
     */
    public User() {
        this.name = null;
        this.lastname = null;
        this.fiscal_code = null;
        this.email = null;
        this.password = null;
        this.id = null;
    }

    /**
     * Constructor for creating a {@code User} object with only an email address.
     *
     * @param email the email address of the user
     */
    public User(String email) {
        this.email = email;
    }

    /**
     * Constructor for creating a {@code User} object with all the user's details.
     *
     * @param name            the first name of the user
     * @param lastname        the last name of the user
     * @param fiscal_code     the fiscal code of the user
     * @param email           the email address of the user
     * @param hashed_password the hashed password of the user
     * @param uuid            the unique identifier of the user
     */
    public User(String name, String lastname, String fiscal_code, String email, String hashed_password, Integer id) {
        this.name = name;
        this.lastname = lastname;
        this.fiscal_code = fiscal_code;
        this.email = email;
        this.password = hashed_password;
        this.id = id;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the user's first name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the user's last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Gets the fiscal code of the user.
     *
     * @return the user's fiscal code
     */
    public String getFiscal_code() {
        return fiscal_code;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the hashed password of the user.
     *
     * @return the user's hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Compares this user to another user based on their email addresses.
     *
     * @param secondUser the user to compare with
     * @return {@code true} if the email addresses are equal, {@code false} otherwise
     */
    public boolean equals(User secondUser) {
        return Objects.equals(this.email, secondUser.email);
    }

    /**
     * Gets the unique identifier (UUID) of the user.
     *
     * @return the user's UUID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of the user's details.
     *
     * @return a string with the user's name, last name, fiscal code, email, and password
     */
    public String print() {
        String result = "";
        result += "Name: " + name + "\n";
        result += "Lastname: " + lastname + "\n";
        result += "Fiscal Code: " + fiscal_code + "\n";
        result += "Email: " + email + "\n";
        result += "Password: " + password;
        return result;
    }
}
