package data

import src.gen.java.org.openapitools.model.Project
import src.gen.java.org.openapitools.model.User

open class MockProjectData {
    companion object{

        fun getProjects(): MutableList<Project>{
            return mutableListOf(
                createMockProject(
                    id = "123456",
                    title = "Test1",
                    dateAdded = "2023-10-13",
                    status = "Planning",
                    members = emptyList(),
                    manager = User()
                ),
                createMockProject(
                    id = "oskd7w",
                    title = "Test2",
                    dateAdded = "2021-07-13",
                    status = "In progress",
                    members = emptyList(),
                    manager = User()
                ),
            )
        }

        fun getProject(): Project{
            return createMockProject(
                    id = "123456",
                    title = "Test1",
                    dateAdded = "2023-10-13",
                    status = "Planning",
                    members = emptyList(),
                    manager = User()
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