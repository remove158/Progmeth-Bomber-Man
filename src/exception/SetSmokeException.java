package exception;

public class SetSmokeException extends Exception  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message;
    public SetSmokeException(String message) {
        super();
        this.message = message;
    }
}