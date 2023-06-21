package com.gaurav.springcloudproject.dto.request;

import com.gaurav.springcloudproject.constant.ValidationConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Data
public class UserRequestDto {
    @NotEmpty(message = "Name cannot be blank")
    private String name;
    @NotEmpty(message = "Email cannot be blank")
    @Pattern(regexp = ValidationConstant.EMAIL_ID_REGEX, message = "enter valid email")
    private String email;
    @NotEmpty(message = "Password cannot be blank")
    private String password;
}
