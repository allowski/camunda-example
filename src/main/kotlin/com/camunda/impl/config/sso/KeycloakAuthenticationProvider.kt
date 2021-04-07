package com.camunda.impl.config.sso

import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.identity.Group
import org.camunda.bpm.engine.rest.security.auth.AuthenticationResult
import org.camunda.bpm.engine.rest.security.auth.impl.ContainerBasedAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.util.StringUtils
import java.util.*
import java.util.function.Consumer
import javax.servlet.http.HttpServletRequest

class KeycloakAuthenticationProvider : ContainerBasedAuthenticationProvider() {
    override fun extractAuthenticatedUser(request: HttpServletRequest, engine: ProcessEngine): AuthenticationResult {

        println("********************************* extract authenticated user :)")


        // Extract user-name-attribute of the OAuth2 token
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        if (authentication !is OAuth2AuthenticationToken || authentication.getPrincipal() !is OidcUser) {

            println("is unsussesfull")
            return AuthenticationResult.unsuccessful()
        }
        val userId = (authentication.getPrincipal() as OidcUser).name

        println("userid > $userId")
        if (StringUtils.isEmpty(userId)) {
            return AuthenticationResult.unsuccessful()
        }

        // Authentication successful
        val authenticationResult = AuthenticationResult(userId, true)
        authenticationResult.groups = getUserGroups(userId, engine)
        return authenticationResult
    }

    private fun getUserGroups(userId: String, engine: ProcessEngine): List<String> {
        val groupIds: MutableList<String> = ArrayList()
        // query groups using KeycloakIdentityProvider plugin
        engine.identityService.createGroupQuery().groupMember(userId).list()
                .forEach(Consumer { g: Group -> groupIds.add(g.id) })
        return groupIds
    }
}
