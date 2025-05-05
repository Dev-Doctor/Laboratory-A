package io.github.devdoctor.BookRecommender.CommonObjects.enums;

public enum RequestMethods {
    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE");

    private final String value;

    RequestMethods(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean check(String method) {
        return method.equals(this.value);
    }
}
