package com.gaurav.springcloudproject.exception;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
public class ProcessNotAllowedException extends RuntimeException{

    public ProcessNotAllowedException(String exception){
        super(exception);
    }
}
