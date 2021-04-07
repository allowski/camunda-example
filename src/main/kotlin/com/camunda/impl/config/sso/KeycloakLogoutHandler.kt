package com.camunda.impl.config.sso

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws


/**
 * Keycloak Logout Handler.
 */
@Service
class KeycloakLogoutHandler(@Value("\${spring.security.oauth2.client.provider.keycloak.authorization-uri}") oauth2UserAuthorizationUri: String) : LogoutSuccessHandler {
    /** Redirect strategy.  */
    private val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()

    /** Keycloak's logout URI.  */
    private var oauth2UserLogoutUri: String? = null

    /**
     * {@inheritDoc}
     */
    @Throws(IOException::class, ServletException::class)
    override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        if (!StringUtils.isEmpty(oauth2UserLogoutUri)) {
            // Calculate redirect URI for Keycloak, something like http://<host:port>/camunda
            val requestUrl = request.requestURL.toString()
            val redirectUri = requestUrl.substring(0, requestUrl.indexOf("/app"))
            // Complete logout URL
            val logoutUrl = "$oauth2UserLogoutUri?redirect_uri=$redirectUri"

            // Do logout by redirecting to Keycloak logout
            LOG.debug("Redirecting to logout URL {}", logoutUrl)
            redirectStrategy.sendRedirect(request, response, logoutUrl)
        }
    }

    companion object {
        /** This class' logger.  */
        private val LOG = LoggerFactory.getLogger(KeycloakLogoutHandler::class.java)
    }

    /**
     * Default constructor.
     * @param oauth2UserAuthorizationUri configured keycloak authorization URI
     */
    init {
        if (!StringUtils.isEmpty(oauth2UserAuthorizationUri)) {
            // in order to get the valid logout uri: simply replace "/auth" at the end of the user authorization uri with "/logout"
            oauth2UserLogoutUri = oauth2UserAuthorizationUri.replace("openid-connect/auth", "openid-connect/logout")
        }
    }
}
