package Main.Java.org.project.LMS.PatronRelated;

public interface IPatronFactory {
    IPatron createPatron(String patronId, String name, String contactInfo);
}