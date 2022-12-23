plugins {
    id("avata.kotlin-conventions")
    id("avata.spring-conventions")
//    id("org.springframework.boot") version "2.7.1"
//    id("io.spring.dependency-management") version "1.1.0"
    kotlin("kapt")
    id("idea")
}
//sprint boot 2.7.1
//springframework 5.3.21
// 2.7.0 5.3.20
val springBootVersion = "2.7.0"
//
//dependencyManagement {
//    imports {
//        mavenBom("org.springframework.boot:spring-boot-dependencies:${springBootVersion}")
//    }
//}

dependencyManagement {
    dependencies {
        dependency("org.springframework.boot:spring-boot:$springBootVersion")
        dependency("org.springframework.boot:spring-boot-autoconfigure:$springBootVersion")
        dependency("org.springframework.boot:spring-boot-configuration-processor:$springBootVersion")
    }
}

idea {
//    module {
//        val kaptMain = file("${project.buildDir}/generated/source/kapt/main")
//        if (!sourceDirs.contains(kaptMain))
//            sourceDirs.add(kaptMain)
//        if (!generatedSourceDirs.contains(kaptMain))
//            generatedSourceDirs.add(kaptMain)
//
//        outputDir = file("${project.buildDir}/classes/main")
//        testOutputDir = file("${project.buildDir}/classes/test")
//    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
//    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    api(project(":avata-core"))
}

tasks.named("compileJava") {
    inputs.files(tasks.named("processResources"))
}
