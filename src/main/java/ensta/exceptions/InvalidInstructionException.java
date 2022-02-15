package ensta.exceptions;

public class InvalidInstructionException extends Exception {
    public InvalidInstructionException() { }

    public InvalidInstructionException(String message){
        super(message);
    }
}
