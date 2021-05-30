package com.hunza.caterer.exception;

public class MandatoryDataMissingException extends RuntimeException
{
    private static final long serialVersionUID = -6009685544255344123L;

    public MandatoryDataMissingException(String message )
    {
        super( message );
    }
}
