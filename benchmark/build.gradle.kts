
plugins {
    alias(libs.plugins.indra.core)
    alias(libs.plugins.indra.licenser)
}

repositories {
    mavenCentral()
}

indra {
    javaVersions {
        target(25)
        minimumToolchain(25)
    }
}

indraSpotlessLicenser {
    licenseHeaderFile(rootProject.file("license_header.txt"))
    newLine(true)
}

dependencies {
    api(libs.annotations)
    api(libs.jspecify)
    api(project(":"))
    runtimeOnly(project(":facets:base"))
    runtimeOnly(project(":facets:caching"))
    runtimeOnly(project(":facets:direct"))
    runtimeOnly(project(":facets:invoke"))
}
