
plugins {
    alias(libs.plugins.indra.core)
    alias(libs.plugins.indra.licenser)
}

repositories {
    mavenCentral()
}

indra {
    javaVersions {
        target(16)
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
    compileOnly(project(":"))
    compileOnly(project(":facets:base"))
}
