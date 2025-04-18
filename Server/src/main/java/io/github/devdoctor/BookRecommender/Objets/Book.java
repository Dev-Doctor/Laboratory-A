package io.github.devdoctor.BookRecommender.Objets;

import java.util.ArrayList;
import java.util.Date;

public class Book {
    int id;
    String title;
    String publisher;
    String description;
    float price;
    Date publish_date;
    ArrayList<Author> authors;
    ArrayList<String> categories;

    public Book(int id, String title, String publisher, String description, float price, Date publish_date) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.description = description;
        this.price = price;
        this.publish_date = publish_date;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public Book() {
        this.id = -1;
        this.title = null;
        this.publisher = null;
        this.description = null;
        this.price = -1;
        this.publish_date = null;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public Book(int bookId, String title, String editorName, float price, Date publishDate) {
        this.id = bookId;
        this.title = title;
        this.publisher = editorName;
        this.description = null;
        this.price = price;
        this.publish_date = publishDate;
        this.authors = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public void addCategory(String category) {
        this.categories.add(category);
    }
}
