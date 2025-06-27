plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta16"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
}

group = "net.qilla"
version = "1.0-1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    flatDir {
        dirs("libs")
    }
}

dependencies {
    paperweight.paperDevBundle("1.21.6-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-api:4.22.0")
    implementation(":QLibrary-1.0.0")
}

tasks {
    shadowJar {
        val outputDir = file(file("C:\\Users\\Richard\\Development\\Servers\\1.21.6\\plugins"))

        mergeServiceFiles()
        archiveClassifier.set("")

        destinationDirectory.set(outputDir)
    }
}