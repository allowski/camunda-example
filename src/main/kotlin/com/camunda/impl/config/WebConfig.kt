package com.camunda.impl.config

import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws


@Configuration
@Component
class WebConfig: OncePerRequestFilter() {


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        response.setHeader("Access-Control-Allow-Origin", "*")
        if ("OPTIONS" == request.method) {
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH")
            response.setHeader("Access-Control-Allow-Headers", "accept, x-requested-with, Content-Type, Accept-Language, Accept-Encoding ,Origin, Access-Control-Request-Method ,Access-Control-Request-Headers, Last-Modified, Cookie, Referer, Authorization")
            response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Accept-Ranges,Content-Encoding,Content-Length,Content-Range")
            response.setHeader("Access-Control-Max-Age", "100")
            response.status = 200
            response.writer.println("test :)")
        }else {
            filterChain.doFilter(request, response)
        }
    }

}
