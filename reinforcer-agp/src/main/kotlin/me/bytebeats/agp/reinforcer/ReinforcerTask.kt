package me.bytebeats.agp.reinforcer

import me.bytebeats.agp.reinforcer.ReinforcerPlugin.Companion.EXTENSION_NAME
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ReinforcerTask : DefaultTask() {
    private val reinforcerDslExt =
        project.extensions.getByName(EXTENSION_NAME) as ReinforcerDslExtension

    init {
        description = "reinforce apks after `build`"
        group = "reinforcer"
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun compress() {
        println(reinforcerDslExt.toString())

        if (!reinforcerDslExt.enabled.or(false)) {
            println("apiKey should be assigned")
            return
        }

        if (reinforcerDslExt.artifactDir.isNullOrEmpty()) {
            println("artifactDir can't be null")
            return
        }
    }
}