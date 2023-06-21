package com.gaurav.springcloudproject.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Data
public class BlogModifyRequest {
    @Positive(message = "id cannot be empty")
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 120, message = "Minimium length is 5 where maximum length is 120")
    private String title;
    @NotBlank(message = "Body cannot be blank")
    private String body;
}
