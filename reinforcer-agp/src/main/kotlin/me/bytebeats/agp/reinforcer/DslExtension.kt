package me.bytebeats.agp.reinforcer

/**
 * Gradle DSL extension for tiny png plugin
 */
open class DslExtension {

    /**
     * enable to reinforce apks
     */
    var enabled: Boolean = true

    /**
     * artifact directories where you DO want to reinforce apks
     */
    var artifactDir: String? = null

    /**
     * reinforcer aar file to load encrypted dex files
     */
    var reinforcerAarFile: String? = null

    /**
     * keystore file to sign apks
     */
    var keyStoreFile: String? = null

    override fun toString(): String {
        return "ReinforcerDslExtension(enabled=$enabled, artifactDir=$artifactDir, reinforcerAarFile=$reinforcerAarFile, keyStoreFile=$keyStoreFile)"
    }
}