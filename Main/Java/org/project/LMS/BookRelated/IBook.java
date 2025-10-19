package Main.Java.org.project.LMS.BookRelated;


/**
 * Defines the contract for a book entity in the library system.
 * This interface ensures that any book object provides essential attributes and functionality,
 * promoting abstraction and allowing for different implementations of books (e.g., ConcreteBook, EBook, etc.)
 * in the future without affecting the core logic of the library system.
 */
public interface IBook {

    /**
     * Returns the title of the book.
     *
     * @return The title as a String.
     */
    String getTitle();

    /**
     * Returns the author of the book.
     *
     * @return The author as a String.
     */
    String getAuthor();

    /**
     * Returns the ISBN (International Standard Book Number) of the book.
     * The ISBN is a unique identifier for the book.
     *
     * @return The ISBN as a String.
     */
    String getISBN();

    /**
     * Returns the publication year of the book.
     *
     * @return The publication year as an integer.
     */
    int getPublicationYear();

    /**
     * Returns the current status of the book (e.g., AVAILABLE, BORROWED, RESERVED, LOST).
     *
     * @return The current BookStatus of the book.
     */
    BookStatus getStatus();

    /**
     * Sets the status of the book.
     * This method allows updating the book's status based on library operations.
     *
     * @param status The new BookStatus for the getBook
     */

    void setStatus(BookStatus status);
}