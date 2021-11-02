import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.0"

    kotlin("jvm") version "1.5.31"
}

group = "moe.quill"
version = "0.0.3"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}
repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/QuillDev/feather")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("feather")
        mergeServiceFiles()
        manifest {
            attributes["Main-Class"] = "moe.quill.featherplug.Feather"
        }
    }
}

tasks {
    build {
        dependsOn("shadowJar")
    }
}