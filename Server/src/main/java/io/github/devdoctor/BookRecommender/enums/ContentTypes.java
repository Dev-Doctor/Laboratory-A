package io.github.devdoctor.BookRecommender.enums;

public enum ContentTypes {
    JSON("application/json"),
    HTML("html");

    private final String value;

    ContentTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    boolean check(String method) {
        return method.equals(this.value);
    }
}
