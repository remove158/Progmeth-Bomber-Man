package entity;

public class SetSmokeException extends Exception  {

    public String message;
    public SetSmokeException(String message) {
        super();
        this.message = message;
    }
}