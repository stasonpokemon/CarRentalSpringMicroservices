package com.carrental.microservices.orderservice.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.PASSPORT_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.PASSPORT_UUID_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_ACTIVE;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_ACTIVE_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_EMAIL;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_EMAIL_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_PASSWORD;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_PASSWORD_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_USERNAME;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_USERNAME_DESCRIPTION;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_UUID;
import static com.carrental.microservices.orderservice.util.swagger.OpenApiConstants.USER_UUID_DESCRIPTION;


/**
 * This class presents a DTO, which is available via UserController and RegistrationController endpoints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    @Schema(example = USER_UUID, description = USER_UUID_DESCRIPTION)
    @JsonProperty(value = "user_id")
    private UUID id;

    @Schema(example = USER_USERNAME, description = USER_USERNAME_DESCRIPTION)
    @JsonProperty(value = "username")
    private String username;

    @Schema(example = USER_PASSWORD, description = USER_PASSWORD_DESCRIPTION)
    @JsonProperty(value = "password")
    private String password;

    @Schema(example = USER_EMAIL, description = USER_EMAIL_DESCRIPTION)
    @JsonProperty(value = "email")
    private String email;

    @Schema(example = USER_ACTIVE, description = USER_ACTIVE_DESCRIPTION)
    @JsonProperty(value = "is_active")
    private boolean active;

    @Schema(example = PASSPORT_UUID, description = PASSPORT_UUID_DESCRIPTION)
    @JsonProperty(value = "passport_id")
    private UUID passportId;
}
