package exception;

public class UseItemException extends Exception  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message;
    public UseItemException(String message) {
        super();
        this.message = message;
    }
}