pluginManagement {
    repositories {
        maven(url = uri("https://maven.aliyun.com/repository/public/"))
        maven(url = uri("https://maven.aliyun.com/repository/gradle-plugin"))
        maven(url = uri("https://www.jitpack.io"))
        google()
//        mavenCentral()
//        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(url = uri("https://maven.aliyun.com/repository/public/"))
        maven(url = uri("https://maven.aliyun.com/repository/gradle-plugin"))
        maven(url = uri("https://www.jitpack.io"))
        google()
//        mavenCentral()
    }
}

rootProject.name = "Caluculation"
include(":app")
