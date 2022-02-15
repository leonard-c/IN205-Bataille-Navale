package ensta.exceptions;

public class WrongEntryException extends Exception {
    private String message;

    public WrongEntryException() { }

    public WrongEntryException(String message){
        super(message);
    }
}
