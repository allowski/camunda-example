package com.camunda.test.controllers

import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent
import org.camunda.bpm.spring.boot.starter.event.PreUndeployEvent
import org.springframework.context.event.EventListener
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController{

    @RequestMapping("/example")
    fun example(@RequestBody inputMap: HashMap<String, String>): HashMap<String, String> {
        println("NEW EXAMPLEX REQUEST")
        var map = HashMap<String, String>()
        println("NEW EXAMPLEX REQUEST")
        inputMap.keys.forEach({
            println("key: $it -> ${inputMap[it]}")
        })
        map["test"] = "A"
        map["testA"] = "A"
        return map
    }

    @EventListener
    fun onPostDeploy(event: PostDeployEvent) {

    }

    @EventListener
    fun onPreUndeploy(event: PreUndeployEvent) {

    }

}
