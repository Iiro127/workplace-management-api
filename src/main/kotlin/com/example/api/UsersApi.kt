package com.example.api

import com.example.controller.UsersController
import jakarta.inject.Inject
import src.gen.java.org.openapitools.api.UsersApi
import src.gen.java.org.openapitools.model.User

class UsersApi: UsersApi {

    @Inject
    lateinit var usersController: UsersController
    override fun createUser(user: User?): User? {
        return try {
           if ( usersController.createUser(user!!)) {
               user
           } else {
               User()
           }
        } catch (e: Exception) {
            throw e
        }
    }
}