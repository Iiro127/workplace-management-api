package com.example.db

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoIterable
import jakarta.inject.Inject
import org.bson.Document
import org.eclipse.microprofile.config.inject.ConfigProperty
import src.gen.java.org.openapitools.model.Project
import src.gen.java.org.openapitools.model.User

class ProjectsDatabaseController {

    @ConfigProperty(name = "database.access.endpoint")
    lateinit var dbAddress: String

    /**
     * Gets database for projects
     *
     * @return MongoCollection<Document>
     */
    fun getDatabase(): MongoCollection<Document>{
        val client = MongoClients.create(dbAddress)
        val database = client.getDatabase("workplace-management-db")

        return database.getCollection("workplace_management-db")
    }

    /**
     * Gets all projects from MongoDB
     *
     * @return List<Project>
     */
    fun getProjects(): List<Project> {
        val projects: MongoIterable<Document> = getDatabase().find()

        return projects.map { doc ->
            Project()
                .id(doc.getString("id"))
                .title(doc.getString("title"))
                .status(doc.getString("status"))
                .dateAdded(doc.getString("dateAdded"))
                .members(doc.getList("members", Document::class.java)?.map { memberDoc ->
                    mapUser(memberDoc)
                } ?: emptyList())
                .manager(doc.get("manager", Document::class.java)?.let { managerDoc ->
                    mapUser(managerDoc)
                })
        }.toList()
    }

    /**
     * Maps users fetched from MongoDB
     *
     * @param doc Document
     * @return User
     */
    private fun mapUser(doc: Document): User {
        return User()
            .id(doc.getString("id"))
            .firstName(doc.getString("firstName"))
            .lastName(doc.getString("lastName"))
            .isActive(doc.getBoolean("isActive"))
    }
}