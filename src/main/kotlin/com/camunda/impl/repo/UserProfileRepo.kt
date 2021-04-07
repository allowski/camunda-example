package com.camunda.impl.repo

import com.camunda.impl.domains.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Component
interface UserProfileRepo: JpaRepository<UserProfile, Long>
