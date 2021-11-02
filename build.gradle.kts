plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "7.1.0"
    kotlin("jvm") version "1.5.31"
}

group = "moe.quill"
version = "0.0.18"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")


}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
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
        register<MavenPublication>("feather") {
            from(components["java"])
            pom {
                name.set("feather")
                description.set("Feather - A Kotlin based PaperMC development helper.")
            }
        }
    }
}



tasks {
    jar {
        from(sourceSets.main.get().output)
        manifest {
            attributes["Manifest-Version"] = "1.0"
            attributes["Main-Class"] = "moe.quill.featherplugin.Feather"
        }
    }
}