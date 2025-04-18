/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender;

import java.util.*;

/**
 * The type Book.
 *
 * @author DevDoctor
 * @since 1.0
 */
public class Book {
    private String title;
    private List<Author> authors;
    private String description;
    private List<String> category;
    private String publisher;
    private float price;
    private Date publish_date;
    private int id;

    /**
     * Instantiates a new Book.
     *
     * @param title         the title
     * @param authors       the authors
     * @param description   the description
     * @param category      the category
     * @param publisher     the publisher
     * @param price         the price
     * @param publish_date  the date the book was published (month,year)
     */
    public Book(String title, List<Author> authors, String description, List<String> category, String publisher, float price, Date publish_date) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.publish_date = publish_date;
        this.id = -1;
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
     * @param publish_date  the date the book was published (month,year)
     */
    public Book(String title, List<Author> authors, String description, List<String> category, String publisher, float price, Date publish_date, int id) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.publish_date = publish_date;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(publish_date);
        return String.valueOf(cal.get(Calendar.MONTH));
    }

    public String getPublish_year() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(publish_date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + authors +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", publish_month='" + getPublish_month() + '\'' +
                ", publish_year='" + getPublish_year() + '\'' +
                ", id=" + id +
                '}';
    }
}

