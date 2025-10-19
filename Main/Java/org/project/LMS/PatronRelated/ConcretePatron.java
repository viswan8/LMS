package Main.Java.org.project.LMS.PatronRelated;

import Main.Java.org.project.LMS.BookRelated.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects; // For Objects.hash()

/**
 * Concrete implementation of the IPatron interface, representing a library member.
 * This class encapsulates patron details and manages their current borrowing list.
 */
public class ConcretePatron implements IPatron {
    private String patronId; // Unique identifier for the patron
    private String name;
    private String contactInfo;
    private List<IBook> borrowedBooks; // List of books currently borrowed by this patron

    /**
     * Constructor to create a new ConcretePatron instance.
     *
     * @param patronId A unique identifier for the patron.
     * @param name The full name of the patron.
     * @param contactInfo The contact information.
     */
    public ConcretePatron(String patronId, String name, String contactInfo) {
        // Basic validation
        if (patronId == null || patronId.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron ID can not be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron name can not be null or empty.");
        }
        if (contactInfo == null || contactInfo.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron contact information can not be null or empty.");
        }

        this.patronId = patronId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.borrowedBooks = new ArrayList<>(); // Initialize an empty list for borrowed books
    }

    @Override
    public String getPatronId() {
        return patronId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContactInfo() {
        return contactInfo;
    }

    @Override
    public void setContactInfo(String contactInfo) {
        if (contactInfo == null || contactInfo.trim().isEmpty()) {
            throw new IllegalArgumentException("Patron contact information can not be null or empty.");
        }
        this.contactInfo = contactInfo;
    }

    @Override
    public List<IBook> getBorrowedBooks() {
        // Return an unmodifiable list to prevent external modification of the patron's borrowed books list
        return Collections.unmodifiableList(borrowedBooks);
    }

    @Override
    public void borrowBook(IBook book) {
        if (book == null) {
            throw new IllegalArgumentException("Can not borrow a null book.");
        }
        if (borrowedBooks.contains(book)) {
            throw new IllegalArgumentException("Patron " + name + " (ID: " + patronId + ") has already borrowed book: " + book.getTitle());
        }
        this.borrowedBooks.add(book);
    }

    @Override
    public void returnBook(IBook book) {
        if (book == null) {
            throw new IllegalArgumentException("Can not return a null book.");
        }
        if (!borrowedBooks.contains(book)) {
            throw new IllegalArgumentException("Patron " + name + " (ID: " + patronId + ") did not borrow book: " + book.getTitle());
        }
        this.borrowedBooks.remove(book);
    }

    /**
     * Overrides the toString method for better logging and readability.
     * @return A string representation of the patron.
     */
    @Override
    public String toString() {
        return "Patron [ID=" + patronId + ", Name=" + name + ", Contact=" + contactInfo +
                ", BorrowedBooksCount=" + borrowedBooks.size() + "]";
    }

    /**
     * Overrides equals method to compare patrons based on their patronId.
     * Patron ID is considered the unique identifier.
     * @param o The object to compare with.
     * @return true if the objects are equal (same patron ID), false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcretePatron that = (ConcretePatron) o;
        return patronId.equals(that.patronId);
    }

    /**
     * Overrides hashCode method to be consistent with the equals method.
     * @return The hash code based on the patronId.
     */
    @Override
    public int hashCode() {
        return Objects.hash(patronId);
    }
}