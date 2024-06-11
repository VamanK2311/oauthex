package com.black.exception;

public class InvalidBookingRequestException extends RuntimeException{
public  InvalidBookingRequestException(String message){
    super(message);
}

}
