package com.camunda.test

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableProcessApplication
class LogicApplication

fun main(args: Array<String>) {
    runApplication<LogicApplication>(*args)
}
