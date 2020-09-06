package com.docomo.digital.transcation.exception;

public class NotEnoughBalanceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public NotEnoughBalanceException(String message){
        super(message);
    }
}
