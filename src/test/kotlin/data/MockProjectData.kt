package data

import org.bson.Document
import src.gen.java.org.openapitools.model.Project
import src.gen.java.org.openapitools.model.User

open class MockProjectData {
    companion object{

        fun getProjects(): MutableList<Document> {
            return mutableListOf(
                createMockProjectDocument(
                    id = "123456",
                    title = "Test1",
                    dateAdded = "2023-10-13",
                    status = "Planning",
                    members = emptyList(),
                    manager = createMockUserDocument()
                ),
                createMockProjectDocument(
                    id = "oskd7w",
                    title = "Test2",
                    dateAdded = "2021-07-13",
                    status = "In progress",
                    members = emptyList(),
                    manager = createMockUserDocument()
                )
            )
        }

        fun createMockProjectDocument(
            id: String,
            title: String,
            dateAdded: String,
            status: String,
            members: List<String>,
            manager: Document
        ): Document {
            return Document("project", Document()
                .append("id", id)
                .append("title", title)
                .append("dateAdded", dateAdded)
                .append("status", status)
                .append("members", members)
                .append("manager", manager)
            )
        }

        fun createMockUserDocument(): Document {
            return Document()
                .append("name", "Default Manager")
                .append("email", "manager@example.com")
        }

        fun getProject(): Document {
            return createMockProjectDocument(
                    id = "654321",
                    title = "Test2",
                    dateAdded = "2023-10-13",
                    status = "Planning",
                    members = emptyList(),
                    manager = createMockUserDocument()
                )
        }
        fun createMockProject(
            id: String,
            title: String,
            dateAdded: String,
            status: String,
            members: List<User>,
            manager: User,
        ):Project {
            val newProject = Project()
            newProject.id = id
            newProject.title = title
            newProject.dateAdded = dateAdded
            newProject.status = status
            newProject.members = members
            newProject.manager = manager

            return newProject
        }
    }
}