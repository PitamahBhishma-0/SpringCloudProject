package com.gaurav.springcloudproject.dto.response;

import lombok.Data;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Data
public class LoginResponse {

    private String userName;
    private String token;

    public LoginResponse(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }
}
