package Main.Java.org.project.LMS.PatronRelated;

import Main.Java.org.project.LMS.BookRelated.*;

import java.util.List;

public interface IPatron {

    /**
     * Returns the unique identifier for the patron.
     * @return The patron ID as a String.
     */
    String getPatronId();

    /**
     * Returns the full name of the patron.
     * @return The patron's name as a String.
     */
    String getName();

    /**
     * Returns the contact information of the patron (e.g., email, phone number).
     * @return The contact information as a String.
     */
    String getContactInfo();

    /**
     * Sets or updates the contact information for the patron.
     * @param contactInfo The new contact information as a String.
     */
    void setContactInfo(String contactInfo); // Allowing updates to contact info

    /**
     * Returns an unmodifiable list of books currently borrowed by this patron.
     * @return A List of IBook objects currently borrowed.
     */
    List<IBook> getBorrowedBooks();

    /**
     * Records that the patron has borrowed a specific book.
     * @param book The IBook object that the patron is borrowing.
     * @throws IllegalArgumentException if the book is null or already borrowed by this patron.
     */
    void borrowBook(IBook book);

    /**
     * Records that the patron has returned a specific book.
     * @param book The IBook object that the patron is returning.
     * @throws IllegalArgumentException if the book is null or not currently borrowed by this patron.
     */
    void returnBook(IBook book);
}