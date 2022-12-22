//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//
//subprojects {
//
////    plugins {
////        kotlin("jvm") version "1.7.20"
////    }
////    plugins {
////        kotlin("jvm") version "1.7.20"
////        id("maven-publish")
////        id("java-library")
////        id("org.jetbrains.dokka") version "1.7.20"
////    }
//}
//
//plugins {
//    kotlin("jvm") version "1.7.20"
//    id("maven-publish")
//    id("java-library")
//    id("org.jetbrains.dokka") version "1.7.20"
//}
//
//group = "ai.avata.spring"
//version = "1.0-SNAPSHOT"
//
//java {
//    withJavadocJar()
//    withSourcesJar()
//}
//
////tasks.create("copyDokka",Copy::class){
////    println("executing copyDokka")
////    dependsOn("dokkaJavadoc")
////    from("$buildDir/dokka/javadoc")
////    into("$buildDir/tmp/javadocJar")
////}
////
////tasks.withType(Javadoc::class){
////    println("executing Javadoc")
////    dependsOn("copyDokka")
//////    copy {
//////        from("$buildDir/dokka/javadoc")
//////        into("$buildDir/tmp/javadocJar")
//////    }
////}
//
////tasks.create("dokkaJavadoc", org.jetbrains.dokka.gradle.DokkaTask::class) {
//////    outputFormat = 'javadoc'
////    outputDirectory.set(File("$buildDir/dokkaJavadoc"))
////}
//
////tasks.create("packageJavadoc", Jar::class) {
////    dependsOn("dokkaJavadoc")
////    from("$buildDir/dokka")
//////    archiveClassifier = "javadoc"
////    archiveClassifier.set("javadoc")
////}
////
////artifacts {
////
////    artifacts {
////        "packageJavadoc"
////    }
////}
//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            groupId = project.group.toString()
//            artifactId = project.name
//            version = project.version.toString()
//
//            from(components["java"])
//        }
//    }
//}
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
//    testImplementation(kotlin("test"))
//}
//
//tasks.test {
//    useJUnitPlatform()
//}
//
//tasks.withType<KotlinCompile> {
//    kotlinOptions.jvmTarget = "1.8"
//}