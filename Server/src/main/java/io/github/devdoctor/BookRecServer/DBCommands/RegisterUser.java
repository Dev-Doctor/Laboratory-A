package io.github.devdoctor.BookRecServer.DBCommands;

public class RegisterUser implements DbCommand {
    String query;

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
