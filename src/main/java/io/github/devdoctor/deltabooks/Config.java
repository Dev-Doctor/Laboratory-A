package io.github.devdoctor.deltabooks;

public class Config {
    private String users_dataset_location;
    private String books_dataset_location;

    public Config() {
        this.users_dataset_location = "";
        this.books_dataset_location = "";
    }

    public String getUsers_dataset_location() {
        return users_dataset_location;
    }

    public void setUsers_dataset_location(String users_dataset_location) {
        this.users_dataset_location = users_dataset_location;
    }

    public String getBooks_dataset_location() {
        return books_dataset_location;
    }

    public void setBooks_dataset_location(String books_dataset_location) {
        this.books_dataset_location = books_dataset_location;
    }
}