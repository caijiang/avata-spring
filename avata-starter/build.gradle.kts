plugins {
    id("avata.kotlin-conventions")
    id("avata.spring-conventions")
//    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.1.0"
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

dependencies {
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation(project(":avata-core"))
}
