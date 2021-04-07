package com.camunda.impl.config.rest

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty


/**
 * Complete Security Configuration Properties for Camunda REST Api.
 */
@Component
@ConfigurationProperties(prefix = "rest.security")
@Validated
class RestApiSecurityConfigurationProperties {
    /**
     * @return the enabled
     */
    /**
     * @param enabled the enabled to set
     */
    /**
     * rest.security.enabled:
     *
     * Rest Security is enabled by default. Switch off by setting this flag to `false`.
     */
    var enabled = true

    /**
     * @return the provider
     */
    /**
     * @param provider the provider to set
     */
    /**
     * rest.security.provider:
     *
     * The name of the spring.security.oauth2.client.provider to use
     */
    var provider: @NotEmpty String? = null

    /**
     * @return the requiredAudience
     */
    /**
     * @param requiredAudience the requiredAudience to set
     */
    /**
     * rest.security.required-audience:
     *
     * Required Audience.
     */
    var requiredAudience: @NotEmpty String? = null
    // ------------------------------------------------------------------------

}
