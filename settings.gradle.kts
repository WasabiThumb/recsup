rootProject.name = "recsup"

pluginManagement {
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    }
}

include(
    ":facets:base",
    ":facets:never",
    ":facets:caching",
    ":facets:invoke",
    ":facets:direct",
    ":benchmark"
)
