package Main.Java.org.project.LMS.PatronRelated;

public class ConcretePatronFactory implements IPatronFactory {
    @Override
    public IPatron createPatron(String patronId, String name, String contactInfo) {
        return new ConcretePatron(patronId, name, contactInfo);
    }
}