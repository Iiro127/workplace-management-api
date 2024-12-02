package com.example.api

import com.example.controller.ProjectsController
import jakarta.enterprise.context.Initialized
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import src.gen.java.org.openapitools.api.ProjectsApi
import src.gen.java.org.openapitools.model.Project

@RequestScoped
class ProjectsApi: ProjectsApi {

    @Inject
    lateinit var projectsController: ProjectsController
    override fun createProject(project: Project?): Project {
        return try {
            if (projectsController.createProject(project!!)){
                project
            } else {
                Project()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun deleteProject(projectId: String?) {
        try {
            projectsController.deleteProject(projectId!!)
        } catch (e: Exception){
            throw e
        }
    }

    override fun findProject(projectId: String?): Project {
        return try {
            projectsController.findProject(projectId!!)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getProjects(): MutableList<Project> {
        return try {
            projectsController.getProjects()
        } catch (e: Exception){
            throw e
        }
    }

    override fun updateProject(projectId: String?, project: Project?): Project {
       return try {
           if (projectsController.updateProject(projectId!!, project!!)){
               project
           } else {
               Project()
           }
       } catch (e: Exception) {
           throw e
       }
    }
}