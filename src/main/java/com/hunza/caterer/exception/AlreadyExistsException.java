package com.hunza.caterer.exception;

public class AlreadyExistsException extends RuntimeException
{
    private static final long serialVersionUID = -3465455419850710042L;

    public AlreadyExistsException(String message )
    {
        super( message );
    }
}
