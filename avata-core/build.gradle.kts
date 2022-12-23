plugins {
    id("avata.kotlin-conventions")
    id("avata.spring-conventions")
}

val springApiVersion = "5.3.20"

dependencies {
//    implementation("io.github.bianjieai:opb-sdk:0.1.11")
    implementation("com.alibaba:fastjson:1.2.83")
    implementation("org.springframework.data:spring-data-commons:2.7.0")
    @Suppress("VulnerableLibrariesLocal")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.apache.commons:commons-lang3:3.12.0")
//    api("org.springframework:spring-context:$springApiVersion")
//    api("org.springframework:spring-web:$springApiVersion")
}
