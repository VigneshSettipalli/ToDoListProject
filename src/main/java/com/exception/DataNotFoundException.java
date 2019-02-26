package com.exception;

public class DataNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;
    private String errorMessage;
 
    public DataNotFoundException() {
		super();
	}
    
    public DataNotFoundException(String errorMessage)
    {
    	super(errorMessage);
    	this.errorMessage = errorMessage;
    }
}
