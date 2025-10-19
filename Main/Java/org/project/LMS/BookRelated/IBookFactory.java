package Main.Java.org.project.LMS.BookRelated;

public interface IBookFactory {
    IBook createBook(String title, String author, String ISBN, int publicationYear);
}