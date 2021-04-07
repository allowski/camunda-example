package com.camunda.impl

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.camunda.connect.Connectors
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@CrossOrigin("*")
@EnableProcessApplication("camunda-keycloack-example")
class LogicApplication

fun main(args: Array<String>) {
    runApplication<LogicApplication>(*args)
}
