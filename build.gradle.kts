plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.0"
    kotlin("jvm") version "1.5.31"
}

group = "moe.quill"
version = "0.0.11"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}
repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
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