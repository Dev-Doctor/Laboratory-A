package io.github.devdoctor.BookRecommender.CommonObjects;

import io.github.devdoctor.BookRecommender.CommonObjects.enums.RequestMethods;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.Responses;

public class test {
    public static void main(String[] args) {
        SimpleLogger logger = SimpleLogger.getInstance();

        logger.debug("Debug Message");
        logger.warn("Warn Message");
        logger.error("Error Message");
        logger.log("Log Message");
        logger.rainbow("Rainbow Message");

        logger.logRequest(RequestMethods.GET, Responses.OK, "api/v1/user", "192.167.123.1", 20);
        logger.errorRequest(RequestMethods.POST, Responses.NOT_ALLOWED, "api/v1/books", "192.168.1.10", new RuntimeException());
    }
}
