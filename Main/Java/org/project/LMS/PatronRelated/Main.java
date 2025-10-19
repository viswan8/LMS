package Main.Java.org.project.LMS.PatronRelated;

import Main.Java.org.project.LMS.BookRelated.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Testing IPatron and ConcretePatron ---");

        // Create some books
        IBook book1 = new ConcreteBook("The Hobbit", "J.R.R. Tolkien", "978-0345339683", 1937);
        IBook book2 = new ConcreteBook("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565", 1925);
        IBook book3 = new ConcreteBook("To Kill a Mockingbird", "Harper Lee", "978-0446310789", 1960);

        // Create a patron
        IPatron patron1 = new ConcretePatron("P001", "Alice Smith", "alice.smith@example.com");
        System.out.println("Created Patron 1: " + patron1);

        // Test borrowing books
        try {
            patron1.borrowBook(book1);
            book1.setStatus(BookStatus.BORROWED); // Manually update book status for this test
            System.out.println("\nPatron 1 borrowed: " + book1.getTitle());
            System.out.println("Patron 1 current borrowed books: " + patron1.getBorrowedBooks());
            System.out.println("Book 1 status: " + book1.getStatus());
        } catch (IllegalArgumentException e) {
            System.err.println("Error borrowing book: " + e.getMessage());
        }

        try {
            patron1.borrowBook(book2);
            book2.setStatus(BookStatus.BORROWED);
            System.out.println("\nPatron 1 borrowed: " + book2.getTitle());
            System.out.println("Patron 1 current borrowed books: " + patron1.getBorrowedBooks());
            System.out.println("Book 2 status: " + book2.getStatus());
        } catch (IllegalArgumentException e) {
            System.err.println("Error borrowing book: " + e.getMessage());
        }

        // Try to borrow same book twice
        try {
            System.out.println("\nAttempting to borrow " + book1.getTitle() + " again...");
            patron1.borrowBook(book1);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught expected error: " + e.getMessage());
        }

        // Test returning books
        try {
            patron1.returnBook(book1);
            book1.setStatus(BookStatus.AVAILABLE); // Manually update book status for this test
            System.out.println("\nPatron 1 returned: " + book1.getTitle());
            System.out.println("Patron 1 current borrowed books: " + patron1.getBorrowedBooks());
            System.out.println("Book 1 status: " + book1.getStatus());
        } catch (IllegalArgumentException e) {
            System.err.println("Error returning book: " + e.getMessage());
        }

        // Try to return a book not borrowed
        try {
            System.out.println("\nAttempting to return " + book3.getTitle() + " (not borrowed)...");
            patron1.returnBook(book3);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught expected error: " + e.getMessage());
        }

        // Test updating patron info
        System.out.println("\nPatron 1 contact before update: " + patron1.getContactInfo());
        patron1.setContactInfo("alice.new@example.com");
        System.out.println("Patron 1 contact after update: " + patron1.getContactInfo());

        // Test equality
        IPatron patron2 = new ConcretePatron("P001", "Alice Smith", "another@example.com"); // Same ID as patron1
        IPatron patron3 = new ConcretePatron("P002", "Bob Johnson", "bob@example.com");

        System.out.println("\nIs Patron 1 equal to Patron 2 (same ID)? " + patron1.equals(patron2)); // Should be true
        System.out.println("Is Patron 1 equal to Patron 3? " + patron1.equals(patron3)); // Should be false

        System.out.println("HashCode Patron 1: " + patron1.hashCode());
        System.out.println("HashCode Patron 2: " + patron2.hashCode()); // Should be same as Patron 1
    }
}
