package com.ems.exception;

public class FacultyNotFoundException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacultyNotFoundException(final String message) {
        super(message);
    }
}