import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("maven-publish")
    kotlin("jvm") version "1.5.31"
}

group = "moe.quill"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("feather")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "moe.quill.testing.Feather"))
        }
    }
}