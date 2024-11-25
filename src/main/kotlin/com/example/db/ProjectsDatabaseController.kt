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
    lateinit var dbAddress: String

    /**
     * Gets database for projects
     *
     * @return MongoCollection<Document>
     */
    fun getDatabase(): MongoCollection<Document> {
        val client = MongoClients.create(dbAddress)
        val database = client.getDatabase("workplace_management-db")
        return database.getCollection("projects")
    }

    /**
     * Gets all projects from database
     *
     * @return List<Project>
     */
    fun getProjectsFromDatabase(): List<Project> {
        val collection = getDatabase().find()

        return collection.map { doc ->
            val projectDoc = doc.get("project", Document::class.java)

            Project()
                .id(projectDoc.getString("id"))
                .title(projectDoc.getString("title"))
                .customer(projectDoc.getString("customer"))
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

    /**
     * Adds a new project to database
     *
     * @param project Project
     * @return Boolean
     */
    fun addProjectToDatabase(project: Project?): Boolean {
        return try {
            val projectDocument = Document()
                .append("project", projectToDocument(project!!))
            getDatabase().insertOne(projectDocument)

            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Finds a project from database based on projectId
     *
     * @param projectId String
     * @return Project
     */
    fun findProjectFromDatabase(projectId: String): Project {
        return getDatabase()
            .find(Document("project.id", projectId))
            .firstOrNull()
            ?.get("project", Document::class.java)
            ?.let { projectDoc ->
                Project()
                    .id(projectDoc.getString("id"))
                    .title(projectDoc.getString("title"))
                    .customer(projectDoc.getString("customer"))
                    .status(projectDoc.getString("status"))
                    .dateAdded(projectDoc.getString("dateAdded"))
                    .members(projectDoc.getList("members", Document::class.java)?.mapNotNull(::mapUser) ?: emptyList())
                    .manager(projectDoc.get("manager", Document::class.java)?.let(::mapUser))
            }!!
    }


    /**
     * Updates project in database based on projectId
     *
     * @param projectId String
     * @param newProject Project
     * @return Boolean
     */
    fun updateProjectInDatabase(projectId: String, newProject: Project): Boolean{
        return try {
            val projectDocument = Document("project", projectToDocument(newProject))
            val result = getDatabase().replaceOne(Document("project.id", projectId), projectDocument)

            result.modifiedCount > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}