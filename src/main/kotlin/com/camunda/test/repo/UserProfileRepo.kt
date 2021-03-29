package com.camunda.test.repo

import com.camunda.test.domains.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Component
interface UserProfileRepo: JpaRepository<UserProfile, Long>
