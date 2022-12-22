plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.7.20")
    implementation("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin:1.7.20")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.7.20")
}

//https://docs.gradle.org/current/userguide/custom_plugins.html#:~:text=You%20can%20put%20the%20source%20for%20the%20plugin,available%20on%20the%20classpath%20of%20the%20build%20script.
//learn more https://quickbirdstudios.com/blog/gradle-kotlin-buildsrc-plugin-android/
//较为完整的案例 https://github.com/skobow/gradle-spring-boot-starter-sample