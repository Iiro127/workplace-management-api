package com.example.api

import com.example.controller.UsersController
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.api.UsersApi
import src.gen.java.org.openapitools.model.User

@RequestScoped
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

    override fun deleteUser(userid: String?): Boolean {
        return try {
            usersController.deleteUser(userid!!)
        } catch (e: Exception){
            throw e
        }
    }

    override fun findUser(userid: String?): User {
        return try {
            usersController.findUser(userid!!)
        } catch (e: Exception){
            throw e
        }
    }

    override fun getUsers(): MutableList<User> {
        return try {
            usersController.getUsers()
        } catch (e: Exception) {
            throw e
        }
    }

    override fun updateUser(userid: String?, user: User?): User {
        return try {
            if (usersController.updateUser(userid!!, user!!)){
                user
            } else {
                User()
            }
        } catch (e: Exception){
            throw e
        }
    }
}