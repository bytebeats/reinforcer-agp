package me.bytebeats.agp.reinforcer

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A Gradle Task to reinforce apks.
 */
class ReinforcerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, ReinforcerDslExtension::class.java)
        project.task(TASK_NAME) { ReinforcerTask::class.java }
        project.tasks.named("build") {//run 'reinforcer' after 'build'
            it.finalizedBy(TASK_NAME)
        }
    }

    companion object {
        const val EXTENSION_NAME = "reinforcer"
        private const val TASK_NAME = "reinforcerTask"
    }
}