package com.dev.bbs.exception;

public class LoginFailedException extends Exception {
	private static final long serialVersionUID = 1L;

	public LoginFailedException(String excep) {
		super(excep);
	}

}
