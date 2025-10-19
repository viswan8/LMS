package Main.Java.org.project.LMS.ObserverRelated;

import Main.Java.org.project.LMS.BookRelated.*;
import Main.Java.org.project.LMS.LendingManager.*;
import Main.Java.org.project.LMS.PatronRelated.*;

public class LibraryService {
    private final IBookRepository bookRepository;
    private final IPatronRepository patronRepository;
    private final ILendingManager lendingManager;
    private final IBookFactory bookFactory; // Injected factory
    private final IPatronFactory patronFactory; // Injected factory

    public LibraryService(IBookRepository bookRepository, IPatronRepository patronRepository,
                          ILendingManager lendingManager, IBookFactory bookFactory, IPatronFactory patronFactory) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.lendingManager = lendingManager;
        this.bookFactory = bookFactory;
        this.patronFactory = patronFactory;
    }

    public void addNewBook(String title, String author, String ISBN, int publicationYear) {
        // Use the factory to create a book instance
        IBook newBook = bookFactory.createBook(title, author, ISBN, publicationYear);
        bookRepository.addBook(newBook);
    }

    public void registerNewPatron(String patronId, String name, String contactInfo) {
        // Use the factory to create a patron instance
        IPatron newPatron = patronFactory.createPatron(patronId, name, contactInfo);
        patronRepository.addPatron(newPatron);
    }

    // Other methods that use lendingManager, etc.
}