package Main.Java.org.project.LMS.ObserverRelated;

/**
 * A concrete observer that logs library events to the console.
 * This demonstrates a simple logging mechanism using the Observer pattern.
 */
public class LoggerObserver implements ILibraryObserver {

    @Override
    public void update(LibraryEvent event) {
        switch (event.getType()) {
            case ERROR:
                System.err.println("LOGGER [ERROR] " + event.toString());
                break;
            case WARNING:
                System.out.println("LOGGER [WARNING] " + event.toString());
                break;
            default:
                System.out.println("LOGGER [INFO] " + event.toString());
                break;
        }
    }
}