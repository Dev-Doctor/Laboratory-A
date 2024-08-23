package io.github.devdoctor.deltabooks;

/**
 * The type User.
 * @author DevDoctor
 * @version 1.0
 */
public class User {
    private String name;
    private String lastname;
    private String fiscal_code;
    private String email;
    private String password;

    public User() {
        this.name = null;
        this.lastname = null;
        this.fiscal_code = null;
        this.email = null;
        this.password = null;
    }

    public User(String name, String lastname, String fiscal_code, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.fiscal_code = fiscal_code;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFiscal_code() {
        return fiscal_code;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
