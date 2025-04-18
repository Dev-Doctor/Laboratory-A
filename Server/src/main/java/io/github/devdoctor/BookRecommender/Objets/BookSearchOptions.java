package io.github.devdoctor.BookRecommender.Objets;

import java.util.ArrayList;
import java.util.List;

public class BookSearchOptions {
    private final int id;
    private final String title;
    private final List<String> authors;
    private final String publisher;
    private final List<String> categories;

    private BookSearchOptions(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.authors = builder.authors;
        this.publisher = builder.publisher;
        this.categories = builder.categories;
    }

    public int getId() {
        return id;
    }

    public boolean hasId() {
        return id > -1;
    }

    public String getTitle() {
        return title;
    }

    public boolean hasTitle() {
        return title != null && !title.isEmpty();
    }

    public List<String> getAuthors() {
        return authors;
    }

    public boolean hasAuthors() {
        return authors != null && !authors.isEmpty();
    }

    public String getPublisher() {
        return publisher;
    }

    public boolean hasPublisher() {
        return publisher != null && !publisher.isEmpty();
    }

    public List<String> getCategories() {
        return categories;
    }

    public boolean hasCategories() {
        return categories != null && !categories.isEmpty();
    }

    public boolean hasNothing() {
        return !(hasId() || hasTitle() || hasAuthors() || hasPublisher() || hasCategories());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String title;
        private List<String> authors;
        private String publisher;
        private List<String> categories;

        private Builder() {
            id = -1;
            title = null;
            authors = null;
            publisher = null;
            categories = null;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder authors(List<String> authors) {
            this.authors = authors;
            return this;
        }

        public Builder addAuthor(String author) {
            if (this.authors == null) {
                this.authors = new ArrayList<>();
            }
            authors.add(author);
            return this;
        }

        public Builder addCategory(String category) {
            if (this.categories == null) {
                this.categories = new ArrayList<>();
            }
            categories.add(category);
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public BookSearchOptions build() { return new BookSearchOptions(this); }
    }

}
