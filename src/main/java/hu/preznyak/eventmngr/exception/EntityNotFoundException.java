package hu.preznyak.eventmngr.exception;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 4313886598081297077L;
	private String errorMessage;

	public EntityNotFoundException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
