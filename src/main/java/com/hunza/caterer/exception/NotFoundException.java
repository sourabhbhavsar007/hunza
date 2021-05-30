package com.hunza.caterer.exception;

public class NotFoundException extends RuntimeException
{
    private static final long serialVersionUID = -8176442826327166141L;

    public NotFoundException(String message )
    {
        super( message );
    }
}