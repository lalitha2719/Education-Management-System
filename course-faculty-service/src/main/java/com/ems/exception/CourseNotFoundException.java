package com.ems.exception;

public class CourseNotFoundException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CourseNotFoundException(final String message) {
        super(message);
    }
}