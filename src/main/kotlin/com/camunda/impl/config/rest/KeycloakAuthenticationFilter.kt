package com.camunda.impl.config.rest

import org.camunda.bpm.engine.IdentityService
import org.camunda.bpm.engine.identity.Group
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.util.StringUtils
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.IOException
import java.lang.Exception
import java.util.*
import java.util.function.Consumer
import javax.servlet.*
import kotlin.jvm.Throws


/**
 * Keycloak Authentication Filter - used for REST API Security.
 */
class KeycloakAuthenticationFilter
/**
 * Creates a new KeycloakAuthenticationFilter.
 * @param identityService access to Camunda's IdentityService
 */(
        /** Access to Camunda's IdentityService.  */
        private val identityService: IdentityService,
        /** Access to the OAuth2 client service.  */
        var clientService: OAuth2AuthorizedClientService) : Filter {

    /**
     * {@inheritDoc}
     */
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        println("********************* doFilter**extractAuthenticatedUser ********************+")

        // Extract user-name-attribute of the JWT / OAuth2 token
        val authentication = SecurityContextHolder.getContext().authentication
        var userId: String? = null
        userId = if (authentication is JwtAuthenticationToken) {
            (authentication as JwtAuthenticationToken).getName()
        } else if (authentication.principal is OidcUser) {
            (authentication.principal as OidcUser).name
        } else {
            throw ServletException("Invalid authentication request token")
        }
        if (StringUtils.isEmpty(userId)) {
            throw ServletException("Unable to extract user-name-attribute from token")
        }
        println("______________ Extracted userId from bearer token: $userId")
        try {
            val groups = getUserGroups(userId)
            for (g in groups){
                println("_________________ group -> $g")
            }
            identityService.setAuthentication(userId, groups)

            println("_________________ group for userId")
            chain.doFilter(request, response)

            println("_________________ doFilter called 3")
        } catch(ex: Exception){

            println("_________________ doFilter called 2")

        } finally {
            println("______________ on finally _________________")
            identityService.clearAuthentication()
        }
    }

    /**
     * Queries the groups of a given user.
     * @param userId the user's ID
     * @return list of groups the user belongs to
     */
    private fun getUserGroups(userId: String?): List<String> {
        val groupIds: MutableList<String> = ArrayList()
        // query groups using KeycloakIdentityProvider plugin
        val list = identityService.createGroupQuery().groupMember(userId).list()
        println("_____________________ LIST ITENS ${list.size}")
        list.forEach(Consumer { g: Group -> groupIds.add(g.id) })
        return groupIds
    }

    companion object {
        /** This class' logger.  */
        private val LOG = LoggerFactory.getLogger(KeycloakAuthenticationFilter::class.java)
    }

}
