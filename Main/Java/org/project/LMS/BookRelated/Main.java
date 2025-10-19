package Main.Java.org.project.LMS.BookRelated;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Testing IBook and ConcreteBook ---");

        // Create a book
        IBook book1 = new ConcreteBook("The Lord of the Rings", "J.R.R. Tolkien", "978-0618260274", 1954);
        System.out.println("Created Book 1: " + book1);
        System.out.println("Is Book 1 available? " + book1.getStatus());

        // Change availability
        book1.setStatus(BookStatus.BORROWED);
        System.out.println("Book 1 after checkout: " + book1);
        System.out.println("Is Book 1 available? " + book1.getStatus());

        // Create another book
        IBook book2 = new ConcreteBook("1984", "George Orwell", "978-0451524935", 1949);
        System.out.println("Created Book 2: " + book2);

        // Test equality
        IBook book3 = new ConcreteBook("The Lord of the Rings", "J.R.R. Tolkien", "978-0618260274", 2000); // Same ISBN, different year
        System.out.println("Created Book 3 (same ISBN as Book 1, different year): " + book3);
        System.out.println("Is Book 1 equal to Book 3 (by ISBN)? " + book1.equals(book3)); // Should be true
        System.out.println("Is Book 1 equal to Book 2? " + book1.equals(book2)); // Should be false

        // Test hashCode
        System.out.println("HashCode Book 1: " + book1.hashCode());
        System.out.println("HashCode Book 3: " + book3.hashCode()); // Should be same as Book 1

        // Test invalid book creation
        try {
            new ConcreteBook(null, "Author", "ISBN123", 2023);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error for null title: " + e.getMessage());
        }
        try {
            new ConcreteBook("Title", "Author", "ISBN123", -10);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error for invalid publication year: " + e.getMessage());
        }
    }
}