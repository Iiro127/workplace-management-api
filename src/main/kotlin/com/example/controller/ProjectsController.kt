package com.example.controller

import com.example.db.ProjectsDatabaseController
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.model.Project

@ApplicationScoped
class ProjectsController {

    @Inject
    lateinit var databaseController: ProjectsDatabaseController

    /**
     * Gets all projects
     *
     * @return MutableList<Project>
     */
    fun getProjects(): MutableList<Project>{
        return databaseController.getProjectsFromDatabase().toMutableList()
    }

    /**
     * Finds a project with projectId
     *
     * @param projectId String
     * @return Project
     */
    fun findProject(projectId: String): Project {
        return databaseController.findProjectFromDatabase(projectId)
    }

    /**
     * Creates a new project
     *
     * @param project Project
     * @return Boolean
     */
    fun createProject(project: Project): Boolean {
        return databaseController.addProjectToDatabase(project)
    }

    /**
     * Updates an existing project
     *
     * @param projectId String
     * @param project Project
     * @return Boolean
     */
    fun updateProject(projectId: String, project: Project): Boolean {
        return databaseController.updateProjectInDatabase(projectId, project)
    }
}