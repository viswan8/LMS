package Main.Java.org.project.LMS.ObserverRelated;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap; // Import HashMap

/**
 * Represents a generic event that occurs within the library system.
 * This class serves as a base for more specific event types, providing
 * common attributes like event type, timestamp, and a map for additional details.
 */
public class LibraryEvent {
    public enum EventType {
        BOOK_ADDED, BOOK_REMOVED, BOOK_UPDATED, BOOK_CHECKED_OUT, BOOK_RETURNED,
        PATRON_ADDED, PATRON_UPDATED,
        ERROR, INFO, WARNING
    }

    private final EventType type;
    private final LocalDateTime timestamp;
    private final String message;
    private final Map<String, String> details; // For additional contextual information

    public LibraryEvent(EventType type, String message, Map<String, String> details) {
        if (type == null) {
            throw new IllegalArgumentException("Event type can not be null.");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Event message can not be null or empty.");
        }

        this.type = type;
        this.timestamp = LocalDateTime.now();
        // Create a new HashMap if details are provided, otherwise empty map.
        this.details = details != null ? Collections.unmodifiableMap(new HashMap<>(details)) : Collections.emptyMap();
        this.message = message;
    }

    public LibraryEvent(EventType type, String message) {
        this(type, message, null);
    }

    public EventType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(timestamp.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("]");
        sb.append(" [").append(type).append("] ");
        sb.append(message);
        if (!details.isEmpty()) {
            sb.append(" Details: ").append(details);
        }
        return sb.toString();
    }
}