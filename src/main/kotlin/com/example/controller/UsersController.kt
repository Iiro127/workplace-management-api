package com.example.controller

import com.example.db.UsersDatabaseController
import jakarta.inject.Inject
import src.gen.java.org.openapitools.model.User

class UsersController {
    @Inject
    lateinit var databaseController: UsersDatabaseController

    fun createUser(user: User): Boolean {
        return databaseController.addUserToDatabase(user)
    }
}