val bootVersion = "2.7.0"

plugins {
    id("avata.kotlin-conventions")
    id("avata.spring-conventions")
    id("org.springframework.boot") version "2.7.0"
}

tasks.withType<AbstractPublishToMaven>().configureEach {
    isEnabled = false
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    implementation(project(":avata-starter"))
}