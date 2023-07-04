rootProject.name = "log-collection"

pluginManagement {
    // by : https://jisungbin.medium.com/코틀린-by-키워드-알아보기-54aa7252febb
    val springBootVersion: String by settings
    val springBootDependencyManagementVersion: String by settings
    val kotlinVersion: String by settings

    // 기본 플러그인 버전을 명시 : https://docs.gradle.org/current/dsl/org.gradle.api.initialization.Settings.html
    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springBootDependencyManagementVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
    }
}