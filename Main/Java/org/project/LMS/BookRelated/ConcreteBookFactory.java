package Main.Java.org.project.LMS.BookRelated;

public class ConcreteBookFactory implements IBookFactory {
    @Override
    public IBook createBook(String title, String author, String ISBN, int publicationYear) {
        return new ConcreteBook(title, author, ISBN, publicationYear);
    }
}