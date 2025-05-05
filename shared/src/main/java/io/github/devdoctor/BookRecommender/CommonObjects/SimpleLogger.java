package io.github.devdoctor.BookRecommender.CommonObjects;

import io.github.devdoctor.BookRecommender.CommonObjects.enums.RequestMethods;
import io.github.devdoctor.BookRecommender.CommonObjects.enums.Responses;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLogger {
    private static SimpleLogger instance;
    private final String FILENAME;

    private SimpleLogger() {
        FILENAME = getTimestamp() + ".log";
    }

    public static SimpleLogger getInstance() {
        if (instance == null) {
            synchronized (SimpleLogger.class) {
                if (instance == null) {
                    instance = new SimpleLogger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        log(LogLevel.INFO, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void rainbow(String message) {
        log(LogLevel.RAINBOW, message);
    }

    public void logRequest(RequestMethods requestMethod, Responses response, String path, String requester, long duration) {
        String logEntry = String.format("[%-19s] %-9s [%s %s] [IP: %s] [%s] [Duration: %dms]",
                getTimestamp(),
                "[INFO]",
                requestMethod,
                path,
                requester,
                response.toFormat(),
                duration);

        System.out.println(LogLevel.INFO.color() + logEntry + LogLevel.RESET.color());
        writeToLogFile(logEntry);
    }

    public void errorRequest(RequestMethods requestMethod, Responses response, String path, String requester, Exception e) {
        String logEntry = String.format("[%-19s] %-9s %-9s %s [%s]",
                getTimestamp(),
                "[ERROR]",
                String.format("[%s %s]", requestMethod, path),
                String.format("[IP: %s]", requester),
                response.toFormat()
        );

        System.out.println(LogLevel.ERROR.color() + logEntry + LogLevel.RESET.color());
        e.printStackTrace();

        writeToLogFile(logEntry + "\n" + e.getMessage());
    }

    private void log(LogLevel logType, String message) {
        String logEntry = String.format("[%-19s] %-9s %s", getTimestamp(), "[" + logType.name() + "]", message);

        if(logType == LogLevel.RAINBOW) {
            System.out.println(rainbownize(logEntry));
        } else {
            System.out.println(logType.color() + logEntry + LogLevel.RESET.color());
        }

        writeToLogFile(logEntry);
    }

    private void writeToLogFile(String logEntry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME, true))) {
            writer.println(logEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    private String rainbownize(String message) {
        final String[] COLORS = {
                "\u001B[31m", // Red
                "\u001B[33m", // Yellow
                "\u001B[32m", // Green
                "\u001B[36m", // Cyan
                "\u001B[34m", // Blue
                "\u001B[35m"  // Magenta
        };

        StringBuilder output = new StringBuilder();
        int colorIndex = 0;

        for (char ch : message.toCharArray()) {
            if (ch != ' ') {
                output.append(COLORS[colorIndex % COLORS.length]).append(ch);
                colorIndex++;
            } else {
                output.append(" ");
            }
        }

        output.append(LogLevel.RESET.color());
        return output.toString();
    }

    public enum LogLevel {
        WARN("\u001B[38;5;208m"),
        ERROR("\u001B[31m"),
        INFO("\u001B[36m"),
        RAINBOW("\u001B[36m"),
        RESET("\u001B[0m"),
        DEBUG("\u001B[32m");

        private final String color;

        LogLevel(String color) {
            this.color = color;
        }

        public String color() {
            return color;
        }
    }
}
