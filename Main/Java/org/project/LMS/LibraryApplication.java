package Main.Java.org.project.LMS;

import Main.Java.org.project.LMS.BookRelated.*;
import Main.Java.org.project.LMS.LendingManager.ILendingManager;
import Main.Java.org.project.LMS.LendingManager.LibraryLendingManager;
import Main.Java.org.project.LMS.ObserverRelated.LoggerObserver;
import Main.Java.org.project.LMS.PatronRelated.*;

import java.util.List; // Import List for Java 8 compatibility
import java.util.stream.Collectors; // Import Collectors for Java 8 stream operations

public class LibraryApplication {

    public static void main(String[] args) {
        // 1. Initialize Factories
        IBookFactory bookFactory = new ConcreteBookFactory();
        IPatronFactory patronFactory = new ConcretePatronFactory();

        // 2. Initialize Repositories
        InMemoryBookRepository bookRepository = new InMemoryBookRepository();
        InMemoryPatronRepository patronRepository = new InMemoryPatronRepository();

        // 3. Initialize Lending Manager
        LibraryLendingManager lendingManager = new LibraryLendingManager(bookRepository, patronRepository); // Use concrete type for addObserver

        // 4. Initialize and Register Observers
        LoggerObserver logger = new LoggerObserver();

        bookRepository.addObserver(logger);
        patronRepository.addObserver(logger);
        lendingManager.addObserver(logger); // Register the lending manager itself as a subject

        System.out.println("--- Library System Initialized ---");
        System.out.println("--- Performing Operations ---");
        System.out.println();

        // --- Book Management ---
        IBook book1 = bookFactory.createBook("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565", 1925);
        bookRepository.addBook(book1);

        IBook book2 = bookFactory.createBook("1984", "George Orwell", "978-0451524935", 1949);
        bookRepository.addBook(book2);

        IBook book3 = bookFactory.createBook("To Kill a Mockingbird", "Harper Lee", "978-0060935467", 1960);
        bookRepository.addBook(book3);

        // Try adding a duplicate book (should trigger error log)
        try {
            IBook duplicateBook = bookFactory.createBook("1984", "George Orwell", "978-0451524935", 1949);
            bookRepository.addBook(duplicateBook);
        } catch (IllegalArgumentException e) {
            // Expected, log handled by observer
        }
        System.out.println();

        // Update a book's status (status is managed internally by lending manager, but we can set it for demonstration)
        book1.setStatus(BookStatus.RESERVED);
        bookRepository.updateBook(book1);
        System.out.println();

        // --- Patron Management ---
        IPatron patron1 = patronFactory.createPatron("P001", "Alice Smith", "alice@example.com");
        patronRepository.addPatron(patron1);

        IPatron patron2 = patronFactory.createPatron("P002", "Bob Johnson", "bob@example.com");
        patronRepository.addPatron(patron2);

        // Try adding a duplicate patron
        try {
            IPatron duplicatePatron = patronFactory.createPatron("P001", "Alice Smith", "alice@example.com");
            patronRepository.addPatron(duplicatePatron);
        } catch (IllegalArgumentException e) {
            // Expected, log handled by observer
        }
        System.out.println();

        // Update patron contact info
        patron1.setContactInfo("alice.smith@newmail.com");
        patronRepository.updatePatron(patron1);
        System.out.println();

        // --- Lending Process ---
        System.out.println("--- Attempting Checkouts ---");
        // Checkout book1 to patron1
        lendingManager.checkoutBook(book1.getISBN(), patron1.getPatronId());
        System.out.println();

        // Checkout book2 to patron1
        lendingManager.checkoutBook(book2.getISBN(), patron1.getPatronId());
        System.out.println();

        // Let's make book1 available again, as it was set to RESERVED for demo
        book1.setStatus(BookStatus.AVAILABLE);
        bookRepository.updateBook(book1); // This update will trigger a log.
        System.out.println();

        // Now checkout book1 to patron2
        lendingManager.checkoutBook(book1.getISBN(), patron2.getPatronId());
        System.out.println();

        // --- Book Return ---
        System.out.println("--- Attempting Returns ---");
        // Patron 1 returns book 2
        lendingManager.returnBook(book2.getISBN(), patron1.getPatronId());
        System.out.println();

        // Try to return a book not borrowed by patron2
        lendingManager.returnBook(book3.getISBN(), patron2.getPatronId());
        System.out.println();

        // --- Search Functionality ---
        System.out.println("--- Performing Searches ---");
        System.out.println("Books by Author 'Orwell':");
        List<IBook> orwellBooks = bookRepository.searchBooks("Orwell", SearchType.BY_AUTHOR);
        for (IBook b : orwellBooks) { // Java 8 forEach loop
            System.out.println("  - " + b.getTitle());
        }
        System.out.println();

        System.out.println("Books by Title 'Gatsby':");
        List<IBook> gatsbyBooks = bookRepository.searchBooks("Gatsby", SearchType.BY_TITLE);
        for (IBook b : gatsbyBooks) { // Java 8 forEach loop
            System.out.println("  - " + b.getTitle());
        }
        System.out.println();

        // --- Final Inventory/Patron State ---
        System.out.println("--- Final Library State ---");
        System.out.println("All Books:");
        for (IBook book : bookRepository.getAllBooks()) { // Java 8 forEach loop
            System.out.println(book);
        }
        System.out.println("\nAll Patrons:");
        for (IPatron p : patronRepository.getAllPatrons()) { // Java 8 forEach loop
            System.out.println(p);
            // Java 8 stream for collecting titles
            System.out.println("  Borrowed Books: " + p.getBorrowedBooks().stream().map(IBook::getTitle).collect(Collectors.toList()));
        }

        System.out.println("\n--- Operations Complete ---");
    }
}