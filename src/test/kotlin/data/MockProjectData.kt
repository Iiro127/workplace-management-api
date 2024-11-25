package data

import org.bson.Document
import src.gen.java.org.openapitools.model.Project
import src.gen.java.org.openapitools.model.User

open class MockProjectData {
    companion object{

        fun getProjects(): MutableList<Project> {
            return mutableListOf(
                createMockProject(
                    id = "123456",
                    title = "Test1",
                    customer = "Apple",
                    dateAdded = "2023-10-13",
                    status = "Planning",
                    members = emptyList(),
                    manager = null
                ),
                createMockProject(
                    id = "oskd7w",
                    title = "Test2",
                    customer = "Kesko",
                    dateAdded = "2021-07-13",
                    status = "In progress",
                    members = emptyList(),
                    manager = null
                )
            )
        }

        fun createMockProjectDocument(
            id: String,
            title: String,
            customer: String,
            dateAdded: String,
            status: String,
            members: List<String>,
            manager: Document
        ): Document {
            return Document("project", Document()
                .append("id", id)
                .append("title", title)
                .append("customer", customer)
                .append("dateAdded", dateAdded)
                .append("status", status)
                .append("members", members)
                .append("manager", manager)
            )
        }

        fun createMockUser(): User {
            return User()
        }

        fun getProject(): Project {
            return createMockProject(
                    id = "654321",
                    title = "Test2",
                    customer = "Microsoft",
                    dateAdded = "2023-10-13",
                    status = "Planning",
                    members = emptyList(),
                    manager = null
                )
        }
        fun createMockProject(
            id: String,
            title: String,
            customer: String,
            dateAdded: String,
            status: String,
            members: List<User>?,
            manager: User?,
        ):Project {
            val newProject = Project()
            newProject.id = id
            newProject.title = title
            newProject.customer = customer
            newProject.dateAdded = dateAdded
            newProject.status = status
            newProject.members = members
            newProject.manager = manager

            return newProject
        }
    }
}