/**
 * Nome: Davide Restelli
 * Matricola: 757198
 * Sede: Como
 */
package io.github.devdoctor.BookRecommender.CommonObjects;

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



    public Book() {
        this.id = -1;
        this.title = null;
        this.description = null;
        this.price = -1;
        this.publisher = null;
        this.publish_date = null;
        this.authors = new ArrayList<>();
        this.category = new ArrayList<>();
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
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.publisher = publisher;
        this.publish_date = publish_date;
        this.authors = authors;
        this.category = category;
    }



    public Book(int bookId, String title, String editorName, float price, Date publishDate) {
        this.id = bookId;
        this.title = title;
        this.description = null;
        this.price = price;
        this.publisher = editorName;
        this.publish_date = publishDate;
        this.authors = new ArrayList<>();
        this.category = new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getCategories() {
        return category;
    }

    public void setCategories(List<String> category) {
        this.category = category;
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

