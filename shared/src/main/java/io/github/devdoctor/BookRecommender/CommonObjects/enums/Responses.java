package io.github.devdoctor.BookRecommender.CommonObjects.enums;

public enum Responses {
    OK(200),
    CREATED(201),

    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    NOT_ALLOWED(405),

    INTERNAL_SERVER_ERROR(500);

    private final int code;

    private Responses(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

    public boolean check(int status) {
        return code == status;
    }

    public String toFormat() {
        return String.valueOf(code) + " " + name();
    }
}
