# Entity Cardinality and Relationships

This document outlines the cardinalities (relationships) between the main entities in the system, presented in a UML-style textual representation.

## Cardinality Between Entities

### IBook (Book) and IBookRepository (Book Repository)

*   An `IBookRepository` contains 0 or more `IBook` objects (`0..*`).
*   An `IBook` is managed by exactly one `IBookRepository` (`1`).
*   **Relationship:** Composition/Aggregation (Repository owns/manages Books)

### IPatron (Patron) and IPatronRepository (Patron Repository)

*   An `IPatronRepository` contains 0 or more `IPatron` objects (`0..*`).
*   An `IPatron` is managed by exactly one `IPatronRepository` (`1`).
*   **Relationship:** Composition/Aggregation (Repository owns/manages Patrons)

### IBook (Book) and IPatron (Patron - via `borrowedBooks` list)

*   An `IPatron` can borrow 0 or more `IBook` objects (`0..*`).
*   An `IBook` can be borrowed by 0 or 1 `IPatron` at any given time (`0..1`). (Assuming a book can only be borrowed by one patron at a time, reflected by `BookStatus.BORROWED`).
*   **Relationship:** Association (Borrowing)

### LibraryLendingManager and IBookRepository

*   A `LibraryLendingManager` uses exactly one `IBookRepository` (`1`).
*   An `IBookRepository` can be used by 0 or more `LibraryLendingManager` instances (`0..*`).
*   **Relationship:** Dependency (Lending Manager depends on Book Repository)

### LibraryLendingManager and IPatronRepository

*   A `LibraryLendingManager` uses exactly one `IPatronRepository` (`1`).
*   An `IPatronRepository` can be used by 0 or more `LibraryLendingManager` instances (`0..*`).
*   **Relationship:** Dependency (Lending Manager depends on Patron Repository)

### ILibrarySubject (Subject) and ILibraryObserver (Observer)

*   An `ILibrarySubject` can have 0 or more `ILibraryObserver` objects registered (`0..*`).
*   An `ILibraryObserver` can observe 0 or more `ILibrarySubject` objects (`0..*`).
*   **Relationship:** Association (Observer Pattern)

## Observer Pattern Implementation Details

In your specific implementation:

*   `InMemoryBookRepository` is a **Subject**.
*   `InMemoryPatronRepository` is a **Subject**.
*   `LibraryLendingManager` is a **Subject**.
*   `LoggerObserver` is an **Observer**.

Therefore, a `LoggerObserver` observes 0 or more instances of `InMemoryBookRepository`, `InMemoryPatronRepository`, and `LibraryLendingManager`.

## Visual Representation of Key Relationships