package com.camunda.test.domains

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.annotation.Generated
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
class UserProfile {

    @Id
    @GeneratedValue
    var id: Long = 0

    lateinit var name: String

    lateinit var email: String

    lateinit var password: String

}
