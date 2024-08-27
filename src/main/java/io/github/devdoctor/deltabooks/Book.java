package io.github.devdoctor.deltabooks;

import java.util.List;
import java.util.UUID;

/**
 * The type Book.
 *
 * @author Davide Restelli
 */
public class Book {
    private String title;
    private List<String> authors;
    private String description;
    private List<String> category;
    private String publisher;
    private float price;
    private String publish_month;
    private String publish_year;
    private String uuid;

    /**
     * Instantiates a new Book.
     *
     * @param title         the title
     * @param authors       the authors
     * @param description   the description
     * @param category      the category
     * @param publisher     the publisher
     * @param price         the price
     * @param publish_month the publish month
     * @param publish_year  the publish year
     */
    public Book(String title, List<String> authors, String description, List<String> category, String publisher, float price, String publish_month, String publish_year) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.publish_month = publish_month;
        this.publish_year = publish_year;
        this.uuid = "";
    }

    /**
     * Instantiates a new Book.
     *
     * @param title         the title
     * @param authors       the authors
     * @param description   the description
     * @param category      the category
     * @param publisher     the publisher
     * @param price         the price
     * @param publish_month the publish month
     * @param publish_year  the publish year
     */
    public Book(String title, List<String> authors, String description, List<String> category, String publisher, float price, String publish_month, String publish_year, String uuid) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.publish_month = publish_month;
        this.publish_year = publish_year;
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
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

    public String getPublish_month() {
        return publish_month;
    }

    public void setPublish_month(String publish_month) {
        this.publish_month = publish_month;
    }

    public String getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(String publish_year) {
        this.publish_year = publish_year;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UUID getRealUUID() {
        return UUID.fromString(uuid);
    }
}

