package com.csk.epay.exceptions;

public class ApplicationException extends RuntimeException{
	
	public ApplicationException() {
	}
	public ApplicationException(String msg) {
		super(msg);
	}
}
