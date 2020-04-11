package exception;

public class UseItemException extends Exception  {

    public String message;
    public UseItemException(String message) {
        super();
        this.message = message;
    }
}