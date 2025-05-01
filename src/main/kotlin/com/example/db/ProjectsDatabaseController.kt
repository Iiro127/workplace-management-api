package com.example.db

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.bson.Document
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.jwt.JsonWebToken
import src.gen.java.org.openapitools.model.Project

@ApplicationScoped
class ProjectsDatabaseController: DatabaseResource() {

    @ConfigProperty(name = "database.access.endpoint")
    lateinit var dbAddress: String

    @Inject
    lateinit var jwt: JsonWebToken

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
                .finishEstimate(projectDoc.getString("finishEstimate"))
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
        println(project)
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
                    .finishEstimate(projectDoc.getString("finishEstimate"))
                    .members(projectDoc.getList("members", Document::class.java)?.mapNotNull(::mapUser) ?: emptyList())
                    .manager(projectDoc.get("manager", Document::class.java)?.let(::mapUser))
            }!!
    }

    /**
     * Deletes project from database based on projectId
     *
     * @param projectId String
     * @return Boolean
     */
    fun deleteProjectFromDatabase(projectId: String): Boolean {
        val result = getDatabase().deleteMany(Document("project.id", projectId))

        return result.deletedCount > 0
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

    fun findProjectsForUserFromDatabase(): MutableList<Project> {
        val collection = getDatabase().find()
        val userId = jwt.claim<String>("ID").orElse(null)
        println(userId)


        return collection.mapNotNull { doc ->
            val projectDoc = doc.get("project", Document::class.java)

            val members = projectDoc.getList("members", Document::class.java)?.map { memberDoc ->
                mapUser(memberDoc)
            } ?: emptyList()

            if (members.any { it.id == userId }) {
                Project()
                    .id(projectDoc.getString("id"))
                    .title(projectDoc.getString("title"))
                    .customer(projectDoc.getString("customer"))
                    .status(projectDoc.getString("status"))
                    .dateAdded(projectDoc.getString("dateAdded"))
                    .finishEstimate(projectDoc.getString("finishEstimate"))
                    .members(members)
                    .manager(projectDoc.get("manager", Document::class.java)?.let { managerDoc ->
                        mapUser(managerDoc)
                    })
            } else {
                null
            }
        }.toMutableList()
    }
}