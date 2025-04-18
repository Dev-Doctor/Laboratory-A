package io.github.devdoctor.BookRecommender.Objets;

public class Errors {


    public static class FieldError {
        String error;
        String field;
        String message;

        public FieldError(String error, String field, String message) {
            this.error = error;
            this.field = field;
            this.message = message;
        }
    }
}
