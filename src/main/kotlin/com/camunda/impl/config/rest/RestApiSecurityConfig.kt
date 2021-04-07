package com.camunda.impl.config.rest

import com.camunda.impl.config.WebConfig
import org.camunda.bpm.engine.IdentityService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.web.access.channel.ChannelProcessingFilter
import javax.inject.Inject
import kotlin.jvm.Throws


/**
 * Optional Security Configuration for Camunda REST Api.
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER - 20)
@ConditionalOnProperty(name = ["rest.security.enabled"], havingValue = "true", matchIfMissing = true)
class RestApiSecurityConfig : WebSecurityConfigurerAdapter() {
    /** Configuration for REST Api security.  */
    @Inject
    lateinit var configProps: RestApiSecurityConfigurationProperties

    /** Access to Camunda's Identity Service.  */
    @Inject
    lateinit var identityService: IdentityService

    /** Access to Spring Security OAuth2 client service.  */
    @Inject
    lateinit var clientService: OAuth2AuthorizedClientService

    /**
     * {@inheritDoc}
     */
    @Throws(Exception::class)
    public override fun configure(http: HttpSecurity) {
        val jwkSetUri = applicationContext!!.environment.getRequiredProperty("spring.security.oauth2.client.provider." + configProps.provider.toString() + ".jwk-set-uri")

        http.addFilterBefore(WebConfig(), ChannelProcessingFilter::class.java)


        http
                .antMatcher("/engine-rest/**")
                .cors()
                .and()
                .antMatcher("/engine-rest/**")
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt().jwkSetUri(jwkSetUri)
    }

    /**
     * Create a JWT decoder with issuer and audience claim validation.
     * @return the JWT decoder
     */
    @Bean
    fun jwtDecoder(): JwtDecoder {
        val issuerUri = applicationContext!!.environment.getRequiredProperty("spring.security.oauth2.client.provider." + configProps.provider.toString() + ".issuer-uri")
        val jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuerUri) as NimbusJwtDecoder
        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(configProps.requiredAudience!!)
        val withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri)
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
        jwtDecoder.setJwtValidator(withAudience)
        return jwtDecoder
    }

    /**
     * Registers the REST Api Keycloak Authentication Filter.
     * @return filter registration
     */
    @Bean
    fun keycloakAuthenticationFilter(): FilterRegistrationBean<KeycloakAuthenticationFilter> {
        val filterRegistration: FilterRegistrationBean<KeycloakAuthenticationFilter> = FilterRegistrationBean<KeycloakAuthenticationFilter>()
        filterRegistration.setFilter(KeycloakAuthenticationFilter(identityService, clientService))
        filterRegistration.order = 102 // make sure the filter is registered after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/engine-rest/*")
        return filterRegistration
    }
}
