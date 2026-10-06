pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "myapp"
include(":app")
include(":ch6_view")
include(":ch8_event")
include(":ch8")
include(":ch7_layout")
include(":week4_homework")
include(":ch8_event2")
include(":ch9_resource")
include(":week5")
include(":ch10_notification")
include(":ch11_jetpack")
