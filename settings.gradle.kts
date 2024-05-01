pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.spring.io/milestone")
    }
}

rootProject.name = "federation-jvm-spring-example"

include(":products-subgraph")
include(":reviews-subgraph")
