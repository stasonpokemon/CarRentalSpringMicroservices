package com.carrental.microservices.userservice.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static com.carrental.microservices.userservice.util.swagger.OpenApiConstants.USER_EMAIL;
import static com.carrental.microservices.userservice.util.swagger.OpenApiConstants.USER_EMAIL_DESCRIPTION;
import static com.carrental.microservices.userservice.util.swagger.OpenApiConstants.USER_PASSWORD;
import static com.carrental.microservices.userservice.util.swagger.OpenApiConstants.USER_PASSWORD_DESCRIPTION;
import static com.carrental.microservices.userservice.util.swagger.OpenApiConstants.USER_USERNAME;
import static com.carrental.microservices.userservice.util.swagger.OpenApiConstants.USER_USERNAME_DESCRIPTION;


/**
 * This class presents a DTO, which is available via UserController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequestDTO {

    @Length(min = 6, message = "Username must be more than 6 symbols")
    @Schema(example = USER_USERNAME, description = USER_USERNAME_DESCRIPTION)
    @JsonProperty(value = "username")
    private String username;

    @Length(min = 6, message = "Password must be more than 6 symbols")
    @Schema(example = USER_PASSWORD, description = USER_PASSWORD_DESCRIPTION)
    @JsonProperty(value = "password")
    private String password;

    @NotBlank(message = "Please fill the email")
    @Email(message = "Please fill the correct email")
    @Schema(example = USER_EMAIL, description = USER_EMAIL_DESCRIPTION)
    @JsonProperty(value = "email")
    private String email;
}

