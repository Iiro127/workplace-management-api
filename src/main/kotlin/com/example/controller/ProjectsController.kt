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
}