import java.util.UUID

plugins {
    id("com.android.library")
    `maven-publish`
}

group = "com.github.AntonSannikov"

android {
    namespace = "org.vosk.models"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
    buildFeatures {
        buildConfig = false
    }
    sourceSets {
        named("main") {
            assets.srcDir("${layout.buildDirectory.get()}/generated/assets")
        }
    }
    publishing {
        singleVariant("release")
    }
}

tasks.register("genUUID") {
    val uuid = UUID.randomUUID().toString()
    val odir = file("${layout.buildDirectory.get()}/generated/assets/model-en-us")
    val ofile = file("$odir/uuid")
    doLast {
        mkdir(odir)
        ofile.writeText(uuid)
    }
}

tasks.named("preBuild").configure {
    dependsOn("genUUID")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.AntonSannikov"
            artifactId = "vosk_model_android"
            version = "0.0.1"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
