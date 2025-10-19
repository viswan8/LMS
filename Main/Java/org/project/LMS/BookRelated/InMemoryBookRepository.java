package Main.Java.org.project.LMS.BookRelated;

import Main.Java.org.project.LMS.ObserverRelated.ILibraryObserver;
import Main.Java.org.project.LMS.ObserverRelated.ILibrarySubject;
import Main.Java.org.project.LMS.ObserverRelated.LibraryEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList; // Thread-safe list for observers

public class InMemoryBookRepository implements IBookRepository, ILibrarySubject { // Implement ILibrarySubject
    private final Map<String, IBook> books;
    private final List<ILibraryObserver> observers; // List to hold observers

    public InMemoryBookRepository() {
        this.books = new ConcurrentHashMap<>();
        this.observers = new CopyOnWriteArrayList<>(); // Initialize observers list
    }

    @Override
    public void addObserver(ILibraryObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null.");
        }
        observers.add(observer);
    }

    @Override
    public void removeObserver(ILibraryObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(LibraryEvent event) {
        for (ILibraryObserver observer : observers) {
            observer.update(event);
        }
    }

    @Override
    public void addBook(IBook book) {
        if (book == null) {
            throw new IllegalArgumentException("Cannot add a null book.");
        }
        if (books.containsKey(book.getISBN())) {
            Map<String, String> details = new HashMap<>();
            details.put("ISBN", book.getISBN());
            details.put("Title", book.getTitle());
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Book with ISBN " + book.getISBN() + " already exists.",
                    details
            );
            notifyObservers(errorEvent);
            throw new IllegalArgumentException("Book with ISBN " + book.getISBN() + " already exists.");
        }
        books.put(book.getISBN(), book);
        // Notify observers about the book addition
        Map<String, String> details = new HashMap<>();
        details.put("ISBN", book.getISBN());
        details.put("Title", book.getTitle());
        details.put("Author", book.getAuthor());
        LibraryEvent event = new LibraryEvent(
                LibraryEvent.EventType.BOOK_ADDED,
                "Book added: " + book.getTitle() + " (ISBN: " + book.getISBN() + ")",
                details
        );
        notifyObservers(event);
    }

    @Override
    public boolean removeBook(String ISBN) {
        if (ISBN == null || ISBN.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty.");
        }
        IBook removedBook = books.remove(ISBN);
        if (removedBook != null) {
            // Notify observers about the book removal
            Map<String, String> details = new HashMap<>();
            details.put("ISBN", ISBN);
            details.put("Title", removedBook.getTitle());
            LibraryEvent event = new LibraryEvent(
                    LibraryEvent.EventType.BOOK_REMOVED,
                    "Book removed: " + removedBook.getTitle() + " (ISBN: " + ISBN + ")",
                    details
            );
            notifyObservers(event);
            return true;
        }
        // Notify observers if removal failed
        Map<String, String> details = new HashMap<>();
        details.put("ISBN", ISBN);
        LibraryEvent warningEvent = new LibraryEvent(
                LibraryEvent.EventType.WARNING,
                "Attempted to remove non-existent book with ISBN: " + ISBN,
                details
        );
        notifyObservers(warningEvent);
        return false;
    }

    @Override
    public void updateBook(IBook updatedBook) {
        if (updatedBook == null) {
            throw new IllegalArgumentException("Cannot update with a null book object.");
        }
        if (!books.containsKey(updatedBook.getISBN())) {
            Map<String, String> details = new HashMap<>();
            details.put("ISBN", updatedBook.getISBN());
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Cannot update book. No book found with ISBN: " + updatedBook.getISBN(),
                    details
            );
            notifyObservers(errorEvent);
            throw new IllegalArgumentException("Cannot update book. No book found with ISBN: " + updatedBook.getISBN());
        }
        books.put(updatedBook.getISBN(), updatedBook); // Replace the old object with the updated one
        // Notify observers about the book update
        Map<String, String> details = new HashMap<>();
        details.put("ISBN", updatedBook.getISBN());
        details.put("Title", updatedBook.getTitle());
        details.put("Status", updatedBook.getStatus().name());
        LibraryEvent event = new LibraryEvent(
                LibraryEvent.EventType.BOOK_UPDATED,
                "Book updated: " + updatedBook.getTitle() + " (ISBN: " + updatedBook.getISBN() + "). New status: " + updatedBook.getStatus(),
                details
        );
        notifyObservers(event);
    }

    @Override
    public IBook getBookByISBN(String ISBN) {
        if (ISBN == null || ISBN.trim().isEmpty()) {
            return null;
        }
        return books.get(ISBN);
    }

    @Override
    public List<IBook> searchBooks(String query, SearchType searchBy) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<IBook> results = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();

        for (IBook book : books.values()) {
            switch (searchBy) {
                case BY_TITLE:
                    if (book.getTitle().toLowerCase().contains(lowerCaseQuery)) {
                        results.add(book);
                    }
                    break;
                case BY_AUTHOR:
                    if (book.getAuthor().toLowerCase().contains(lowerCaseQuery)) {
                        results.add(book);
                    }
                    break;
                case BY_ISBN:
                    if (book.getISBN().equalsIgnoreCase(query)) {
                        results.add(book);
                    }
                    break;
            }
        }
        // Notify observers about the search operation (optional, could be verbose)
        Map<String, String> details = new HashMap<>();
        details.put("Query", query);
        details.put("SearchType", searchBy.name());
        details.put("ResultsCount", String.valueOf(results.size()));
        LibraryEvent event = new LibraryEvent(
                LibraryEvent.EventType.INFO,
                "Searched for '" + query + "' by " + searchBy + ". Found " + results.size() + " results.",
                details
        );
        notifyObservers(event);
        return Collections.unmodifiableList(results);
    }

    @Override
    public List<IBook> getAllBooks() {
        return Collections.unmodifiableList(new ArrayList<>(books.values()));
    }
}