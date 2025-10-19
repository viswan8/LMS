# Library Management System (LMS)

This project implements a foundational Library Management System in Java, designed with object-oriented principles, emphasizing maintainability, extensibility, and separation of concerns. It provides core functionalities for managing books and library patrons, along with a lending process and an event-driven logging mechanism using the Observer pattern.

## Core Features

-   **Book Management:**
    -   Add, remove, and update book records.
    -   Books have attributes: title, author, ISBN (unique), publication year, and status (Available, Borrowed, Reserved, Lost).
    -   Search for books by title, author, or ISBN.
-   **Patron Management:**
    -   Add new library members and update their contact information.
    -   Patrons have attributes: unique ID, name, and contact information.
    -   Track the borrowing history for each patron.
-   **Lending Process:**
    -   Checkout books to patrons.
    -   Return borrowed books.
    -   Automatic update of book status and patron's borrowed list during lending operations.
-   **Inventory Management:**
    -   Maintain a real-time record of available and borrowed books.
-   **Event-Driven Logging (Observer Pattern):**
    -   Key system events (e.g., book added, book borrowed, patron updated, errors) trigger notifications.
    -   A `LoggerObserver` captures these events and prints detailed logs to the console, decoupling logging from business logic.

## Project Structure

The project is organized into several packages, reflecting different modules and concerns:

-   `Main.Java.org.project.LMS.BookRelated`: Contains interfaces (`IBook`, `IBookFactory`, `IBookRepository`), concrete implementations (`ConcreteBook`, `ConcreteBookFactory`, `InMemoryBookRepository`), and related enums (`BookStatus`, `SearchType`) for managing books.
-   `Main.Java.org.project.LMS.PatronRelated`: Contains interfaces (`IPatron`, `IPatronFactory`, `IPatronRepository`), and concrete implementations (`ConcretePatron`, `ConcretePatronFactory`, `InMemoryPatronRepository`) for managing library patrons.
-   `Main.Java.org.project.LMS.LendingManager`: Contains the `ILendingManager` interface and its concrete implementation `LibraryLendingManager`, responsible for handling book checkout and return processes.
-   `Main.Java.org.project.LMS.ObserverRelated`: Defines the Observer pattern interfaces (`ILibraryObserver`, `ILibrarySubject`) and a concrete `LibraryEvent` class, along with the `LoggerObserver` for event logging.
-   `Main.Java.org.project.LMS`: Contains the `LibraryApplication` class, which serves as the entry point for demonstrating the system's functionalities.

## Design Patterns Applied

-   **Interface Segregation Principle (ISP):** Achieved through granular interfaces like `IBook`, `IPatron`, `IBookRepository`, `IPatronRepository`, `ILendingManager`, `ILibraryObserver`, `ILibrarySubject`.
-   **Dependency Inversion Principle (DIP):** Dependencies are injected via interfaces (e.g., `LibraryLendingManager` takes `IBookRepository` and `IPatronRepository`), promoting loose coupling.
-   **Factory Method Pattern:** `IBookFactory` and `IPatronFactory` abstract the creation of `IBook` and `IPatron` objects, making it easy to introduce new types of books or patrons without altering client code.
-   **Observer Pattern:** `ILibrarySubject` (implemented by repositories and lending manager) notifies `ILibraryObserver` (e.g., `LoggerObserver`) about significant events, providing a decoupled and extensible event-handling mechanism.

## Getting Started

### Prerequisites

-   Java Development Kit (JDK) 8 or higher.

### How to Run

1.  **Clone the repository (if applicable):**
    ```bash
    git clone <repository-url>
    cd LibraryManagementSystem
    ```
2.  **Compile the Java files:**
    Navigate to the `src` directory (or wherever your `Main.Java` package resides) and compile all `.java` files. If you're using an IDE like IntelliJ IDEA or Eclipse, it will handle compilation automatically.
    ```bash
    # Example command from the project root, assuming 'src' contains Main.Java
    javac -d out $(find src -name "*.java")
    ```
3.  **Run the `LibraryApplication`:**
    ```bash
    java -cp out Main.Java.org.project.LMS.LibraryApplication
    ```
    You will see the output of the system operations, including detailed logs from the `LoggerObserver`.

## Example Output

The application will print a sequence of logs demonstrating:
-   Book additions, updates, and attempts to add duplicates.
-   Patron additions, updates, and attempts to add duplicates.
-   Successful and failed book checkout operations.
-   Successful and failed book return operations.
-   Search operations and their results.
-   The final state of all books and patrons, including borrowed books.

Example log entry from `LoggerObserver`: