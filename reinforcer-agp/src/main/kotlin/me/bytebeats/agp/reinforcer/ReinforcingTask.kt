package me.bytebeats.agp.reinforcer

import me.bytebeats.agp.reinforcer.ReinforcingPlugin.Companion.EXTENSION_NAME
import me.bytebeats.agp.reinforcer.util.*
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

class ReinforcingTask : DefaultTask() {
    private val reinforcerDslExt =
        project.extensions.getByName(EXTENSION_NAME) as DslExtension

    init {
        description = "reinforce apks after `build`"
        group = "reinforcer"
        outputs.upToDateWhen { false }
    }

    @TaskAction
    fun compress() {
        println(reinforcerDslExt.toString())

        if (!reinforcerDslExt.enabled.or(false)) {
            println("apk reinforcing is disabled")
            return
        }

        if (reinforcerDslExt.artifactDir.isNullOrEmpty()) {
            println("artifactDir can't be null")
            return
        }
        if (reinforcerDslExt.reinforcerAarFile.isNullOrEmpty()) {
            println("reinforcer aar file can't be null")
            return
        }
        if (reinforcerDslExt.keyStoreFile.isNullOrEmpty()) {
            println("keystore file can't be null")
            return
        }
        val dir = File(reinforcerDslExt.artifactDir!!)
        val apks = dir.listFiles() { file -> file.name.endsWith(".apk").or(false) }
        if (apks.isNullOrEmpty()) {
            println("No apks in ${reinforcerDslExt.artifactDir}")
            return
        }
        for (apk in apks) {
            reinforce(
                dir.absolutePath,
                apk.name.substring(0, apk.name.indexOf('.')),
                reinforcerDslExt.reinforcerAarFile!!,
                reinforcerDslExt.keyStoreFile!!
            )
        }
    }

    companion object {
        /**
         * reinforce apk
         */
        fun reinforce(
            apkDir: String,
            apkFileName: String,
            reinforcingAarFile: String,
            keyStoreFile: String
        ) {
            val aarUnzipDir = "$apkDir/aar-unzip"
            buildDexFromAar(reinforcingAarFile, aarUnzipDir)
            val srcUnzipPath = "$apkDir/src-apk-unzip"
            val apkFile = "$apkDir/$apkFileName"
            encryptDex(apkFile, srcUnzipPath)
            val unsignedApkPath = "$apkDir/$apkFileName-unsigned.apk"
            rebuildDexIntoApk(srcUnzipPath, unsignedApkPath)
            val alignedUnsignedApkPath = "$apkDir/$apkFileName-unsigned-aligned.apk"
            alignUnsignedApk(unsignedApkPath, alignedUnsignedApkPath)
            val signedApkPath = "$apkDir/$apkFileName-signed-aligned.apk"
            signApk(alignedUnsignedApkPath, keyStoreFile, alignedUnsignedApkPath)
        }
    }
}