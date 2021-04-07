package com.camunda.impl.controllers

import com.camunda.impl.domains.UserProfile
import com.camunda.impl.repo.UserProfileRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RestController

@RestController
class UserProfileController {

    @Autowired
    lateinit var userRepo: UserProfileRepo

    @RequestMapping(method = [POST], path = ["/user"])
    fun persistUser(@RequestBody user: UserProfile): UserProfile {
        var newUser=userRepo.save(user)
        return newUser
    }

}
