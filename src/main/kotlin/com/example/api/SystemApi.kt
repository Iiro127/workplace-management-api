package com.example.api

import jakarta.annotation.security.PermitAll
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.jwt.JsonWebToken
import src.gen.java.org.openapitools.api.SystemApi

@RequestScoped
@PermitAll
class SystemApi: SystemApi {

    @Inject
    lateinit var jwt: JsonWebToken

    override fun ping(): String {
        val firstName = jwt.claim<String>("given_name").orElse("Unknown")
        val lastName = jwt.claim<String>("family_name").orElse("Unknown")
        val email = jwt.claim<String>("email").orElse("Unknown")

        return """
                Pong 
                Username: ${jwt.name}
                Subject: ${jwt.subject}
                Groups: ${jwt.groups}
                Email: $email
                First Name: $firstName
                Last Name: $lastName
            """.trimIndent()
    }
}