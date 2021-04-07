package com.camunda.impl.config.sso

import org.camunda.bpm.webapp.impl.security.auth.ContainerBasedAuthenticationFilter
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.filter.ForwardedHeaderFilter
import java.util.*
import javax.inject.Inject
import kotlin.jvm.Throws


/**
 * Camunda Web application SSO configuration for usage with KeycloakIdentityProviderPlugin.
 */
@ConditionalOnMissingClass("org.springframework.impl.context.junit4.SpringJUnit4ClassRunner")
@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 10)
class WebAppSecurityConfig : WebSecurityConfigurerAdapter() {
    @Inject
    private val keycloakLogoutHandler: KeycloakLogoutHandler? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        println("**containerBasedAuthenticationFilter.configure**")
        http
                .csrf().ignoringAntMatchers("/api/**", "/engine-rest/**")
                .and()
                .requestMatchers().antMatchers("/**").and()
                .authorizeRequests { authorizeRequests ->
                    authorizeRequests
                            .antMatchers("/app/**", "/api/**", "/lib/**")
                            .authenticated()
                            .anyRequest()
                            .permitAll()
                }
                .oauth2Login()
                .and()
                .logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/app/**/logout"))
                .logoutSuccessHandler(keycloakLogoutHandler)
    }

    @Bean
    fun containerBasedAuthenticationFilter(): FilterRegistrationBean<*> {
        println("**containerBasedAuthenticationFilter**")
        val filterRegistration: FilterRegistrationBean<*> = FilterRegistrationBean<ContainerBasedAuthenticationFilter>()
        filterRegistration.filter= ContainerBasedAuthenticationFilter()
        filterRegistration.initParameters = Collections.singletonMap("authentication-provider", "com.camunda.impl.config.sso.KeycloakAuthenticationProvider")
        filterRegistration.order = 101 // make sure the filter is registered after the Spring Security Filter Chain
        filterRegistration.addUrlPatterns("/app/*")
        return filterRegistration
    }

    // The ForwardedHeaderFilter is required to correctly assemble the redirect URL for OAUth2 login.
    // Without the filter, Spring generates an HTTP URL even though the container route is accessed through HTTPS.
    @Bean
    fun forwardedHeaderFilter(): FilterRegistrationBean<ForwardedHeaderFilter> {
        println("**forwardedHeaderFilter**")
        val filterRegistrationBean = FilterRegistrationBean<ForwardedHeaderFilter>()
        filterRegistrationBean.setFilter(ForwardedHeaderFilter())
        filterRegistrationBean.order = Ordered.HIGHEST_PRECEDENCE
        return filterRegistrationBean
    }

    @Bean
    @Order(0)
    fun requestContextListener(): RequestContextListener {
        return RequestContextListener()
    }
}
