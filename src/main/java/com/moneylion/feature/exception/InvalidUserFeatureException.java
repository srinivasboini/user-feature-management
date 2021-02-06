package com.moneylion.feature.exception;

/**
 * InvalidUserFeatureException holds the exception message during saving the
 * user feature relation. Refer AppExceptionHandler.handleInvalidUserFeatureException
 * for handling logic
 *
 * @author srinivas
 * @version 1.0
 * @since 2021-02-06
 */

public class InvalidUserFeatureException extends RuntimeException{

    public InvalidUserFeatureException(){}

    public InvalidUserFeatureException(String message){
        super(message) ;
    }
}
