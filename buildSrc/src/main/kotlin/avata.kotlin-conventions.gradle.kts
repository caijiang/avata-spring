import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("maven-publish")
    id("java-library")
    id("org.jetbrains.dokka")
}

group = "ai.avata.spring"
version = "1.0-SNAPSHOT"

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        maven {
//            https://docs.gradle.org/current/samples/sample_publishing_credentials.html
            credentials(PasswordCredentials::class)
            isAllowInsecureProtocol = true
            val releasesRepoUrl = "http://47.99.219.2:8081/repository/maven-releases/"
            val snapshotsRepoUrl = "http://47.99.219.2:8081/repository/maven-snapshots/"
            val v = project.findProperty("fixedVersion")?.toString() ?: project.version.toString()
            url = uri(if (v.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.findProperty("fixedVersion")?.toString() ?: project.version.toString()

            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}