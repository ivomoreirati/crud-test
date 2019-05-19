package br.com.crud.exceptions;

@SuppressWarnings("serial")
public class PeopleBadRequestException extends RuntimeException {

    public PeopleBadRequestException(String message, Object... args) {
    	super(String.format(message, args));
    }
}
