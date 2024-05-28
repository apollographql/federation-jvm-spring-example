pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "federation-jvm-spring-example"

include(":products-subgraph")
include(":reviews-subgraph")
