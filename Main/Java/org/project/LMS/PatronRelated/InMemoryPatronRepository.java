package Main.Java.org.project.LMS.PatronRelated;

import Main.Java.org.project.LMS.ObserverRelated.ILibraryObserver;
import Main.Java.org.project.LMS.ObserverRelated.ILibrarySubject;
import Main.Java.org.project.LMS.ObserverRelated.LibraryEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryPatronRepository implements IPatronRepository, ILibrarySubject { // Implement ILibrarySubject
    private final Map<String, IPatron> patrons;
    private final List<ILibraryObserver> observers; // List to hold observers

    public InMemoryPatronRepository() {
        this.patrons = new ConcurrentHashMap<>();
        this.observers = new CopyOnWriteArrayList<>(); // Initialize observers list
    }

    @Override
    public void addObserver(ILibraryObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null.");
        }
        observers.add(observer);
    }

    @Override
    public void removeObserver(ILibraryObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(LibraryEvent event) {
        for (ILibraryObserver observer : observers) {
            observer.update(event);
        }
    }

    @Override
    public void addPatron(IPatron patron) {
        if (patron == null) {
            throw new IllegalArgumentException("Can not add a null patron.");
        }
        if (patrons.containsKey(patron.getPatronId())) {
            Map<String, String> details = new HashMap<>();
            details.put("PatronID", patron.getPatronId());
            details.put("Name", patron.getName());
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Patron with ID " + patron.getPatronId() + " already exists.",
                    details
            );
            notifyObservers(errorEvent);
            throw new IllegalArgumentException("Patron with ID " + patron.getPatronId() + " already exists.");
        }
        patrons.put(patron.getPatronId(), patron);
        // Notify observers about the patron addition
        Map<String, String> details = new HashMap<>();
        details.put("PatronID", patron.getPatronId());
        details.put("Name", patron.getName());
        details.put("Contact", patron.getContactInfo());
        LibraryEvent event = new LibraryEvent(
                LibraryEvent.EventType.PATRON_ADDED,
                "Patron added: " + patron.getName() + " (ID: " + patron.getPatronId() + ")",
                details
        );
        notifyObservers(event);
    }

    @Override
    public void updatePatron(IPatron updatedPatron) {
        if (updatedPatron == null) {
            throw new IllegalArgumentException("Can not update with a null patron object.");
        }
        if (!patrons.containsKey(updatedPatron.getPatronId())) {
            Map<String, String> details = new HashMap<>();
            details.put("PatronID", updatedPatron.getPatronId());
            LibraryEvent errorEvent = new LibraryEvent(
                    LibraryEvent.EventType.ERROR,
                    "Can not update patron. No patron found with ID: " + updatedPatron.getPatronId(),
                    details
            );
            notifyObservers(errorEvent);
            throw new IllegalArgumentException("Can not update patron. No patron found with ID: " + updatedPatron.getPatronId());
        }
        patrons.put(updatedPatron.getPatronId(), updatedPatron); // Replace the old object with the updated one
        // Notify observers about the patron update
        Map<String, String> details = new HashMap<>();
        details.put("PatronID", updatedPatron.getPatronId());
        details.put("Name", updatedPatron.getName());
        details.put("Contact", updatedPatron.getContactInfo());
        LibraryEvent event = new LibraryEvent(
                LibraryEvent.EventType.PATRON_UPDATED,
                "Patron updated: " + updatedPatron.getName() + " (ID: " + updatedPatron.getPatronId() + "). New contact: " + updatedPatron.getContactInfo(),
                details
        );
        notifyObservers(event);
    }

    @Override
    public IPatron getPatronById(String patronId) {
        if (patronId == null || patronId.trim().isEmpty()) {
            return null;
        }
        return patrons.get(patronId);
    }

    @Override
    public List<IPatron> getAllPatrons() {
        return Collections.unmodifiableList(new ArrayList<>(patrons.values()));
    }
}