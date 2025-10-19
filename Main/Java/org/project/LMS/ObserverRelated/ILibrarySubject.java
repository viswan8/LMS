package Main.Java.org.project.LMS.ObserverRelated;

/**
 * Defines the contract for a subject that observers can register with to receive notifications.
 * Any class implementing this interface can have observers attached, detached, and can notify them.
 */
public interface ILibrarySubject {
    /**
     * Registers an observer to receive notifications from this subject.
     * @param observer The ILibraryObserver to register.
     */
    void addObserver(ILibraryObserver observer);

    /**
     * Unregisters an observer so it no longer receives notifications.
     * @param observer The ILibraryObserver to unregister.
     */
    void removeObserver(ILibraryObserver observer);

    /**
     * Notifies all registered observers about a new event.
     * @param event The LibraryEvent to send to observers.
     */
    void notifyObservers(LibraryEvent event);
}