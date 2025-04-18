module io.github.devdoctor.shared {
    exports io.github.devdoctor.BookRecommender.CommonObjects;

    opens io.github.devdoctor.BookRecommender.CommonObjects to com.google.gson;
}