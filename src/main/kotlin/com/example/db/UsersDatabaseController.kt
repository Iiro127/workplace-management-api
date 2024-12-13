package com.example.db

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import jakarta.enterprise.context.ApplicationScoped
import org.bson.Document
import org.eclipse.microprofile.config.inject.ConfigProperty
import src.gen.java.org.openapitools.model.User

@ApplicationScoped
class UsersDatabaseController: DatabaseResource() {

    @ConfigProperty(name = "database.access.endpoint")
    lateinit var dbAddress: String

    /**
     * Gets database for users
     *
     * @return MongoCollection<Document>
     */
    fun getDatabase(): MongoCollection<Document> {
        val client = MongoClients.create(dbAddress)
        val database = client.getDatabase("workplace_management-db")
        return database.getCollection("users")
    }

    /**
     * Adds user to database
     *
     * @param user User
     * @return Boolean
     */
    fun addUserToDatabase(user: User): Boolean {
        return try {
            val userDocument = Document()
                .append("user", userToDocument(user))
            getDatabase().insertOne(userDocument)

            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Gets all users from database
     *
     * @return List<User>
     */
    fun getUsersFromDatabase(): List<User> {
        val collection = getDatabase().find()

        return collection.map { doc ->
            val userDoc = doc.get("user", Document::class.java)

            User()
                .id(userDoc.getString("id"))
                .firstName(userDoc.getString("firstName"))
                .lastName(userDoc.getString("lastName"))
                .isActive(userDoc.getBoolean("isActive"))

        }.toList()
    }

    /**
     * Finds a user from database based on userid
     *
     * @param userid String
     * @return User
     */
    fun findUserFromDatabase(userid: String): User {
        return getDatabase()
            .find(Document("user.id", userid))
            .firstOrNull()
            ?.get("user", Document::class.java)
            ?.let { userDoc ->
                User()
                    .id(userDoc.getString("id"))
                    .firstName(userDoc.getString("firstName"))
                    .lastName(userDoc.getString("lastName"))
                    .isActive(userDoc.getBoolean("isActive"))
            }!!
    }

    /**
     * Updates user in the database
     *
     * @param userid String
     * @param newUser User
     *
     * @return Boolean
     */
    fun updateUserInDatabase(userid: String, newUser: User): Boolean {
        return try {
            val userDocument = Document("user", userToDocument(newUser))
            val result = getDatabase().replaceOne(Document("user.id", userid), userDocument)

            result.modifiedCount > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}