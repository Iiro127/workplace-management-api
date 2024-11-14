package com.example.db

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import jakarta.inject.Inject
import org.bson.Document
import org.eclipse.microprofile.config.inject.ConfigProperty
import src.gen.java.org.openapitools.model.Project

class ProjectsDatabaseController {

    @ConfigProperty(name = "database.access.endpoint")
    lateinit var dbAddress: String

    fun getDatabase(): MongoCollection<Document>{
        val client = MongoClients.create(dbAddress)
        val database = client.getDatabase("workplace-management-db")

        return database.getCollection("workplace_management-db")
    }

    fun getProjects(): List<Project>{


        return emptyList()
    }
}