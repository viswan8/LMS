package Main.Java.org.project.LMS.BookRelated;


/**
 * Defines the contract for a book entity in the library system.
 * This interface ensures that any book object provides essential attributes and functionality with
 * abstraction and allowing for different implementations of books
 * in the future without affecting the core logic of the library system.
 */
public interface IBook {

    String getTitle();

    String getAuthor();

    String getISBN();

    int getPublicationYear();

    BookStatus getStatus();

    void setStatus(BookStatus status);
}