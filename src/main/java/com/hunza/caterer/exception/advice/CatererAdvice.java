package com.hunza.caterer.exception.advice;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.hunza.caterer.exception.AlreadyExistsException;
import com.hunza.caterer.exception.MandatoryDataMissingException;
import com.hunza.caterer.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoDataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class CatererAdvice
{
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFoundException (NotFoundException e)
    {
        return e.getMessage ();
    }

    @ExceptionHandler(MandatoryDataMissingException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public String handleMandatoryDataMissingException (MandatoryDataMissingException e)
    {
        return e.getMessage ();
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleAlreadyExistsException (AlreadyExistsException e)
    {
        return e.getMessage ();
    }

    @ExceptionHandler(MongoDataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleMongoDataIntegrityViolationException (MongoDataIntegrityViolationException e)
    {
        return e.getMessage ();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleDataIntegrityViolationException (DataIntegrityViolationException e)
    {
        return e.getMessage ();
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public String handleSQLIntegrityConstraintViolationException (SQLIntegrityConstraintViolationException e)
    {
        return e.getMessage ();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public String handleSQLException (SQLException e)
    {
        return e.getMessage ();
    }

    @ExceptionHandler(MismatchedInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleMismatchedInputException (MismatchedInputException e)
    {
        return e.getMessage ();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleMethodArgumentNotValidException (MethodArgumentNotValidException e)
    {
        return "Request validation failed due to : " + e.getLocalizedMessage();
    }
}
