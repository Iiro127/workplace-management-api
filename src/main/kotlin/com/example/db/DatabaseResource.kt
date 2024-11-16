package com.example.db

import org.bson.Document
import src.gen.java.org.openapitools.model.User

abstract class DatabaseResource {
    /**
     * Maps user fetched from database
     *
     * @param doc Document
     * @return User
     */
    fun mapUser(doc: Document): User {
        return User()
            .id(doc.getString("id"))
            .firstName(doc.getString("firstName"))
            .lastName(doc.getString("lastName"))
            .isActive(doc.getBoolean("isActive"))
    }
}