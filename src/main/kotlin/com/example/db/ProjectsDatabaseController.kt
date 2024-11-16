package com.example.db

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoIterable
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.bson.Document
import org.eclipse.microprofile.config.inject.ConfigProperty
import src.gen.java.org.openapitools.model.Project
import src.gen.java.org.openapitools.model.User

@ApplicationScoped
class ProjectsDatabaseController: DatabaseResource() {

    @ConfigProperty(name = "database.access.endpoint")
    private lateinit var dbAddress: String

    /**
     * Gets database for projects
     *
     * @return MongoCollection<Document>
     */
    fun getDatabase(): MongoIterable<Document>{
        val client = MongoClients.create(dbAddress)
        val database = client.getDatabase("workplace_management-db")
        val collection = database.getCollection("projects")

        return collection.find()
    }

    /**
     * Gets all projects from database
     *
     * @return List<Project>
     */
    fun getProjectsFromDatabase(): List<Project> {

        return getDatabase().map { doc ->
            val projectDoc = doc.get("project", Document::class.java)

            Project()
                .id(projectDoc.getString("id"))
                .title(projectDoc.getString("title"))
                .status(projectDoc.getString("status"))
                .dateAdded(projectDoc.getString("dateAdded"))
                .members(projectDoc.getList("members", Document::class.java)?.map { memberDoc ->
                    mapUser(memberDoc)
                } ?: emptyList())
                .manager(projectDoc.get("manager", Document::class.java)?.let { managerDoc ->
                    mapUser(managerDoc)
                })
        }.toList()
    }
}