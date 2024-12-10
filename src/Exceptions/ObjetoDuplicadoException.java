package Exceptions;

public class ObjetoDuplicadoException extends Exception {
	public ObjetoDuplicadoException() {
	}

	public ObjetoDuplicadoException(String message) {
		super(message);
	}

	public ObjetoDuplicadoException(Throwable cause) {
		super(cause);
	}

	public ObjetoDuplicadoException(String message, Throwable cause) {
		super(message, cause);
	}
}
