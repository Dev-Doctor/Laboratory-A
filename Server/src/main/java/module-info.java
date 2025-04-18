open module io.github.devdoctor.server {
    requires jbcrypt;
    requires commons.cli;

    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires jdk.httpserver;
    requires org.apache.tomcat.embed.core;
    requires java.desktop;
    requires com.google.gson;
    requires ch.qos.logback.core;

    exports io.github.devdoctor.BookRecommender;
    exports io.github.devdoctor.BookRecommender.enums;
}