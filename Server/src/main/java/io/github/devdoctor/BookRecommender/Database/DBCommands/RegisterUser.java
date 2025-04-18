/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.Database.DBCommands;

public class RegisterUser implements DbCommand {
    String query = "INSERT INTO users (UserId, userName, email, passwordHash) VALUES (default, ?, ?, ?)";

    /*
    * Check if a user is valid
    * add user
    * INSERT INTO USERS VALUES (default, 'username', 'email', 'passwordhash');
    */

    @Override
    public boolean execute() {
        return false;
    }
}
