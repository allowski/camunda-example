package com.camunda.impl.config.rest

import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt


/**
 * Token validator for audience claims.
 */
class AudienceValidator
/**
 * Creates a new audience validator
 * @param audience the required audience
 */(
        /** The required audience.  */
        private val audience: String) : OAuth2TokenValidator<Jwt> {

    /**
     * {@inheritDoc}
     */
    override fun validate(jwt: Jwt): OAuth2TokenValidatorResult {
        println("validate audience")
        return if (jwt.audience.contains(audience)) {
            OAuth2TokenValidatorResult.success()
        } else OAuth2TokenValidatorResult.failure(
                OAuth2Error("invalid_token", "The required audience is missing", null))
    }

}
