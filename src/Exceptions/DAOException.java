package Exceptions;

public class DAOException extends Exception {

	public DAOException() {
	}

	public DAOException(String s) {
		super(s);
	}

	public DAOException(Throwable throwable) {
		super(throwable);
	}

	public DAOException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
