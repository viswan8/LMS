package Main.Java.org.project.LMS.PatronRelated;

import java.util.List;

public interface IPatronRepository {

    /**
     * Adds a new patron to the repository.
     * @param patron The IPatron object to add.
     * @throws IllegalArgumentException if the patron is null or a patron with the same ID already exists.
     */
    void addPatron(IPatron patron);

    /**
     * Updates an existing patron in the repository. The patron is identified by their ID.
     * @param updatedPatron The IPatron object with updated details.
     * @throws IllegalArgumentException if the updatedPatron is null or no patron with its ID exists.
     */
    void updatePatron(IPatron updatedPatron);

    /**
     * Retrieves a patron by their unique patron ID.
     * @param patronId The ID of the patron to retrieve.
     * @return The IPatron object if found, null otherwise.
     */
    IPatron getPatronById(String patronId);

    /**
     * Returns a list of all patrons in the repository.
     * @return An unmodifiable List of all IPatron objects.
     */
    List<IPatron> getAllPatrons();
}