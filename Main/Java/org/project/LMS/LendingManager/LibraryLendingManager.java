package Main.Java.org.project.LMS.LendingManager;

import Main.Java.org.project.LMS.BookRelated.BookStatus;
import Main.Java.org.project.LMS.BookRelated.IBook;
import Main.Java.org.project.LMS.BookRelated.IBookRepository;
import Main.Java.org.project.LMS.ObserverRelated.ILibraryObserver;
import Main.Java.org.project.LMS.ObserverRelated.ILibrarySubject;
import Main.Java.org.project.LMS.ObserverRelated.LibraryEvent;
import Main.Java.org.project.LMS.PatronRelated.IPatron;
import Main.Java.org.project.LMS.PatronRelated.IPatronRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class LibraryLendingManager implements ILendingManager, ILibrarySubject {

    private final IBookRepository bookRepository;
    private final IPatronRepository patronRepository;
    private final List<ILibraryObserver> observers;

    public LibraryLendingManager(IBookRepository bookRepository, IPatronRepository patronRepository) {
        if (bookRepository == null) {
            throw new IllegalArgumentException("Book repository cannot be null.");
        }
        if (patronRepository == null) {
            throw new IllegalArgumentException("Patron repository cannot be null.");
        }
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.observers = new CopyOnWriteArrayList<>();
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
    public boolean checkoutBook(String bookISBN, String patronId) {
        if (bookISBN == null || bookISBN.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ISBN cannot be null or empty for checkout.");
        }
        if (patronId == null || patronId.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron ID cannot be null or empty for checkout.");
        }

        IBook book = bookRepository.getBookByISBN(bookISBN);
        IPatron patron = patronRepository.getPatronById(patronId);

        if (book == null) {
            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("PatronID", patronId);
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Checkout failed. Book with ISBN " + bookISBN + " not found.",
                    details
            );
            notifyObservers(errorEvent);
            return false;
        }
        if (patron == null) {
            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("PatronID", patronId);
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Checkout failed. Patron with ID " + patronId + " not found.",
                    details
            );
            notifyObservers(errorEvent);
            return false;
        }

        if (book.getStatus() != BookStatus.AVAILABLE) {
            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("PatronID", patronId);
            details.put("BookTitle", book.getTitle());
            details.put("CurrentStatus", book.getStatus().name());
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Checkout failed. Book '" + book.getTitle() + "' (ISBN: " + bookISBN + ") is not AVAILABLE. Current status: " + book.getStatus(),
                    details
            );
            notifyObservers(errorEvent);
            return false;
        }

        try {
            // Update book status
            book.setStatus(BookStatus.BORROWED);
            // This will call InMemoryBookRepository.updateBook, which itself notifies observers.
            bookRepository.updateBook(book);

            // Add book to patron's borrowed list
            patron.borrowBook(book);
            // This will call InMemoryPatronRepository.updatePatron, which itself notifies observers.
            patronRepository.updatePatron(patron);

            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("BookTitle", book.getTitle());
            details.put("PatronID", patronId);
            details.put("PatronName", patron.getName());
            LibraryEvent successEvent = new LibraryEvent(
                    LibraryEvent.EventType.BOOK_CHECKED_OUT,
                    "Book '" + book.getTitle() + "' (ISBN: " + bookISBN + ") checked out to Patron '" + patron.getName() + "' (ID: " + patronId + ").",
                    details
            );
            notifyObservers(successEvent);
            return true;
        } catch (IllegalArgumentException e) {
            // Revert book status if patron.borrowBook failed after book.setStatus
            book.setStatus(BookStatus.AVAILABLE);
            // This will call InMemoryBookRepository.updateBook, which itself notifies observers.
            bookRepository.updateBook(book);

            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("BookTitle", book.getTitle());
            details.put("PatronID", patronId);
            details.put("PatronName", patron.getName());
            details.put("Reason", e.getMessage());
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Checkout failed for book '" + book.getTitle() + "' and patron '" + patron.getName() + "': " + e.getMessage(),
                    details
            );
            notifyObservers(errorEvent);
            return false;
        }
    }

    @Override
    public boolean returnBook(String bookISBN, String patronId) {
        if (bookISBN == null || bookISBN.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ISBN cannot be null or empty for return.");
        }
        if (patronId == null || patronId.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron ID cannot be null or empty for return.");
        }

        IBook book = bookRepository.getBookByISBN(bookISBN);
        IPatron patron = patronRepository.getPatronById(patronId);

        if (book == null) {
            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("PatronID", patronId);
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Return failed. Book with ISBN " + bookISBN + " not found.",
                    details
            );
            notifyObservers(errorEvent);
            return false;
        }
        if (patron == null) {
            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("PatronID", patronId);
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Return failed. Patron with ID " + patronId + " not found.",
                    details
            );
            notifyObservers(errorEvent);
            return false;
        }

        if (book.getStatus() != BookStatus.BORROWED && book.getStatus() != BookStatus.RESERVED) {
            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("BookTitle", book.getTitle());
            details.put("PatronID", patronId);
            details.put("CurrentStatus", book.getStatus().name());
            LibraryEvent warningEvent = new LibraryEvent(
                    LibraryEvent.EventType.WARNING,
                    "Book '" + book.getTitle() + "' (ISBN: " + bookISBN + ") is not currently BORROWED or RESERVED. Current status: " + book.getStatus() + ". Attempting return.",
                    details
            );
            notifyObservers(warningEvent);
        }

        try {
            // Remove book from patron's borrowed list
            patron.returnBook(book);
            // This will call InMemoryPatronRepository.updatePatron, which itself notifies observers.
            patronRepository.updatePatron(patron);

            // Update book status
            book.setStatus(BookStatus.AVAILABLE);
            // This will call InMemoryBookRepository.updateBook, which itself notifies observers.
            bookRepository.updateBook(book);

            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("BookTitle", book.getTitle());
            details.put("PatronID", patronId);
            details.put("PatronName", patron.getName());
            LibraryEvent successEvent = new LibraryEvent(
                    LibraryEvent.EventType.BOOK_RETURNED,
                    "Book '" + book.getTitle() + "' (ISBN: " + bookISBN + ") returned by Patron '" + patron.getName() + "' (ID: " + patronId + ").",
                    details
            );
            notifyObservers(successEvent);
            return true;
        } catch (IllegalArgumentException e) {
            Map<String, String> details = new HashMap<>();
            details.put("BookISBN", bookISBN);
            details.put("BookTitle", book.getTitle());
            details.put("PatronID", patronId);
            details.put("PatronName", patron.getName());
            details.put("Reason", e.getMessage());
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Return failed for book '" + book.getTitle() + "' and patron '" + patron.getName() + "': " + e.getMessage(),
                    details
            );
            notifyObservers(errorEvent);
            return false;
        }
    }
}