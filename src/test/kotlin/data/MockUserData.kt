package data

import src.gen.java.org.openapitools.model.Project
import src.gen.java.org.openapitools.model.User

open class MockUserData {
    companion object {
        fun getUser(): User {
            return createMockUser(
                id = "654321",
                firstName = "Markku",
                lastName = "Kanerva",
                isActive = true
            )
        }
        fun createMockUser(
            id: String,
            firstName: String,
            lastName: String,
            isActive: Boolean
        ): User {
            val newUser = User()
            newUser.id = id
            newUser.firstName = firstName
            newUser.lastName = lastName
            newUser.isActive = isActive

            return newUser
        }
    }
}