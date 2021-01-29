package hu.preznyak.eventmngr.exception;

public class EventNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 769712574933311646L;

	public EventNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
