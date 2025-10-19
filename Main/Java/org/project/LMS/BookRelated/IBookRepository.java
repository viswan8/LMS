package Main.Java.org.project.LMS.BookRelated;

import java.util.List;

public interface IBookRepository {

    /**
     * Adds a new book to the repository.
     * @param book The IBook object to add.
     * @throws IllegalArgumentException if the book is null or a book with the same ISBN already exists.
     */
    void addBook(IBook book);

    /**
     * Removes a book from the repository by its ISBN.
     * @param ISBN The ISBN of the book to remove.
     * @return true if the book was found and removed, false otherwise.
     */
    boolean removeBook(String ISBN);

    /**
     * Updates an existing book in the repository. The book is identified by its ISBN.
     * @param updatedBook The IBook object with updated details.
     * @throws IllegalArgumentException if the updatedBook is null or no book with its ISBN exists.
     */
    void updateBook(IBook updatedBook);

    /**
     * Retrieves a book by its unique ISBN.
     * @param ISBN The ISBN of the book to retrieve.
     * @return The IBook object if found, null otherwise.
     */
    IBook getBookByISBN(String ISBN);

    /**
     * Searches for books based on a query and a specified search type.
     * @param query The search term (e.g., title, author name, ISBN).
     * @param searchBy The criteria for searching (e.g., BY_TITLE, BY_AUTHOR, BY_ISBN).
     * @return A List of IBook objects matching the search criteria. Returns an empty list if no matches.
     */
    List<IBook> searchBooks(String query, SearchType searchBy);

    /**
     * Returns a list of all books in the repository.
     * @return An unmodifiable List of all IBook objects.
     */
    List<IBook> getAllBooks();
}
