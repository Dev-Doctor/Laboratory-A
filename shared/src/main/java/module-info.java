module io.github.devdoctor.shared {
    requires jbcrypt;
    requires java.desktop;
    exports io.github.devdoctor.BookRecommender.CommonObjects;
    exports io.github.devdoctor.BookRecommender.CommonObjects.enums;

    opens io.github.devdoctor.BookRecommender.CommonObjects to com.google.gson;
}