package hu.preznyak.eventmngr.exception;

public class DateIntervalException extends Exception {

	private static final long serialVersionUID = 4381765294079040862L;
	private String errorMessage;

	public DateIntervalException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}	

}
