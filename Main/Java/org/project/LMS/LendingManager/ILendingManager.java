package Main.Java.org.project.LMS.LendingManager;

public interface ILendingManager {

    /**
     * Processes the checkout of a book to a patron.
     * @param bookISBN The ISBN of the book to be checked out.
     * @param patronId The ID of the patron checking out the book.
     * @return true if the checkout was successful, false otherwise (e.g., book not available, patron not found).
     * @throws IllegalArgumentException if bookISBN or patronId are null or empty.
     */
    boolean checkoutBook(String bookISBN, String patronId);

    /**
     * Processes the return of a book from a patron.
     * @param bookISBN The ISBN of the book to be returned.
     * @param patronId The ID of the patron returning the book.
     * @return true if the return was successful, false otherwise (e.g., book not borrowed by this patron).
     * @throws IllegalArgumentException if bookISBN or patronId are null or empty.
     */
    boolean returnBook(String bookISBN, String patronId);
}