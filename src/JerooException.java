/**
 * 
 */

/**
 * @author jeff
 *
 */
public class JerooException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Jeroo jeroo;
	
	/**
	 * 
	 */
	public JerooException(Jeroo jeroo) {
		super();
		this.jeroo = jeroo;
	}
	/**
	 * @param message
	 * @param cause
	 */
	public JerooException(Jeroo jeroo, String message, Throwable cause) {
		super(message, cause);
		this.jeroo = jeroo;
	}
	/**
	 * @param message
	 */
	public JerooException(Jeroo jeroo, String message) {
		super(message);
		this.jeroo = jeroo;
	}
	/**
	 * @param cause
	 */
	public JerooException(Jeroo jeroo, Throwable cause) {
		super(cause);
		this.jeroo = jeroo;
	}
	
	/**
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return super.getMessage()+"\n  caused by "+jeroo.toString();
	}
	
	/**
	 * @return the jeroo
	 */
	public Jeroo getJeroo() {
		return jeroo;
	}
	
}
