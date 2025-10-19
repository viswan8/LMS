package Main.Java.org.project.LMS.ObserverRelated;

/**
 * Defines the contract for an observer that reacts to library events.
 * Any class implementing this interface can register to receive notifications
 * when an event occurs in the library system.
 */
public interface ILibraryObserver {
    /**
     * Called when a new library event occurs.
     * @param event The LibraryEvent object containing details about the event.
     */
    void update(LibraryEvent event);
}