package Main.Java.org.project.LMS.BookRelated;

/**
 * Concrete implementation of the IBook interface, representing a physical book.
 * This class encapsulates the core attributes of a book and manages its availability status.
 */
public class ConcreteBook implements IBook {
    private String title;
    private String author;
    private String ISBN; // Unique identifier
    private int publicationYear;
    private BookStatus isAvailable;
    private BookStatus status;


    /**
     * Constructor to create a new ConcreteBook instance.
     *
     * @param title The title of the book.
     * @param author The author of the book.
     * @param ISBN The International Standard Book Number, which must be unique.
     * @param publicationYear The year the book was published.
     */
    public ConcreteBook(String title, String author, String ISBN, int publicationYear) {
        // Basic validation for critical fields
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
        if (ISBN == null || ISBN.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ISBN cannot be null or empty.");
        }
        if (publicationYear <= 0) {
            throw new IllegalArgumentException("Publication year must be a positive value.");
        }

        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.status = BookStatus.AVAILABLE; // When a book object created book is available by default
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getISBN() {
        return ISBN;
    }

    @Override
    public int getPublicationYear() {
        return publicationYear;
    }

    @Override
    public BookStatus getStatus() { // Changed return type and method name
        return status;
    }

    @Override
    public void setStatus(BookStatus status) { // Changed parameter type and method name
        if (status == null) {
            throw new IllegalArgumentException("Book status cannot be null.");
        }
        this.status = status;
    }


    /**
     * Overrides the toString method for better logging and readability.
     * @return A string representation of the book.
     */
    @Override
    public String toString() {
        return "Book [Title=" + title + ", Author=" + author + ", ISBN=" + ISBN +
                ", PublicationYear=" + publicationYear + ", Available=" + isAvailable + "]";
    }

    /**
     * Overrides equals method to compare books based on their ISBN.
     * ISBN is considered the unique identifier.
     * @param obj The object to compare with.
     * @return true if the objects are equal (same ISBN), false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ConcreteBook that = (ConcreteBook) obj;
        return ISBN.equals(that.ISBN);
    }

    /**
     * Overrides hashCode method to be consistent with the equals method.
     * @return The hash code based on the ISBN.
     */
    @Override
    public int hashCode() {
        return ISBN.hashCode();
    }
}