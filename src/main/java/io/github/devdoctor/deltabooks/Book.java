package io.github.devdoctor.deltabooks;

import java.util.List;

public class Book {
    private String title;
    private List<String> authors;
    private String description;
    private List<String> category;
    private String publisher;
    private float price;
    private String publish_month;
    private String publish_year;

    public Book(String title, List<String> authors, String description, List<String> category, String publisher, float price, String publish_month, String publish_year) {
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.publish_month = publish_month;
        this.publish_year = publish_year;
    }

    public String getCategories() {
        String r = "";
        for(String cat : category) {
            r += cat + "; ";
        }
        return r;
    }
}

