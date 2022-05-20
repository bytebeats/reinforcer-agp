package me.bytebeats.agp.reinforcer

/**
 * Gradle DSL extension for tiny png plugin
 */
open class ReinforcerDslExtension {

    /**
     * enable to reinforce apks
     */
    var enabled: Boolean = true

    /**
     * artifact directories where you DO want to reinforce apks
     */
    var artifactDir: String? = null

    override fun toString(): String {
        return "ReinforcerDslExtension(enabled=$enabled, artifactDir=$artifactDir)"
    }


}