package com.gaurav.springcloudproject.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ServerResponse {

    private String message;
    private boolean isSuccess;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public static ResponseEntity<Object> successResponse(String msg) {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setMessage(msg);
        serverResponse.setSuccess(true);
        return new ResponseEntity<>(serverResponse, HttpStatus.OK);
    }
    public static ResponseEntity<Object> exceptionResponse(String msg, HttpStatus httpStatus) {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setMessage(msg);
        serverResponse.setSuccess(false);
        return new ResponseEntity<>(serverResponse, httpStatus);
    }
}
