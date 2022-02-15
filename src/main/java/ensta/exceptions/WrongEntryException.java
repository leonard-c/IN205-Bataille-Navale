package ensta.exceptions;

public class WrongEntryException extends Exception {

    public WrongEntryException() { }

    public WrongEntryException(String message){
        super(message);
    }
}
