package com.example.controller

import com.example.db.UsersDatabaseController
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.model.User

@ApplicationScoped
class UsersController {
    @Inject
    lateinit var databaseController: UsersDatabaseController

    /**
     * Creates a new user
     *
     * @param user User
     * @return Boolean
     */
    fun createUser(user: User): Boolean {
        return databaseController.addUserToDatabase(user)
    }

    /**
     * Gets all users
     *
     * @return MutableList<User>
     */
    fun getUsers(): MutableList<User>{
        return databaseController.getUsersFromDatabase().toMutableList()
    }

    fun findUser(userid: String): User{
        return databaseController.findUserFromDatabase(userid)
    }

    fun updateUser(userid: String, user: User): Boolean {
        return databaseController.updateUserInDatabase(userid, user)
    }

    fun deleteUser(userid: String): Boolean {
        return databaseController.deleteUserInDatabase(userid)
    }
}