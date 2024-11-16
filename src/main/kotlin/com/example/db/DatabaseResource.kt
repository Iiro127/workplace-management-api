package com.example.db

import org.bson.Document
import src.gen.java.org.openapitools.model.Project
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

    /**
     * Converts project to MongoDB document
     *
     * @param project Project
     * @return Document
     */
    fun projectToDocument(project: Project): Document {
        return Document()
            .append("id", project.id)
            .append("title", project.title)
            .append("dateAdded", project.dateAdded)
            .append("status", project.status)
            .append("members", project.members.map { user ->
                userToDocument(user)
            })
            .append("manager", project.manager?.let { userToDocument(it) })
    }

    /**
     * Converts user to MongoDB document
     *
     * @param user User
     * @return Document
     */
    fun userToDocument(user: User): Document {
        return Document()
            .append("id", user.id)
            .append("firstName", user.firstName)
            .append("lastName", user.lastName)
            .append("isActive", user.isActive)
    }
}
