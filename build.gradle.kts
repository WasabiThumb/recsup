
allprojects {
    apply(plugin = "java-library")
    group = "io.github.wasabithumb"
    version = "0.1.1"
}

//

plugins {
    id("java-library")
    alias(libs.plugins.indra.core)
    alias(libs.plugins.indra.licenser)
    alias(libs.plugins.indra.publishing)
    alias(libs.plugins.indra.sonatype)
}

description = "Java 8 library for reading Java 14+ records"

repositories {
    mavenCentral()
}

indra {
    github("WasabiThumb", "recsup")
    apache2License()
    javaVersions {
        target(8)
        minimumToolchain(25)
    }
    configurePublications {
        artifactId = "recsup"
        pom {
            name = "RecSup"
            inceptionYear = "2025"
            developers {
                developer {
                    id = "wasabithumb"
                    name = "Xavier Pedraza"
                    url = "https://github.com/WasabiThumb"
                }
            }
        }
    }
}

indraSpotlessLicenser {
    licenseHeaderFile(rootProject.file("license_header.txt"))
    newLine(true)
}

sourceSets.test {
    multirelease {
        alternateVersions(25)
    }
}

dependencies {
    // Annotations
    api(libs.annotations)
    api(libs.jspecify)

    // JUnit 5
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.compileJava {
    options.compilerArgs.addAll(listOf(
        "-Xlint:-options", // Silence "source value 8 is obsolete" - kind of the point of the library!
        "-Xdoclint:-reference", // Silence "reference not found" - javadocs reference Java 16+ symbols
    ))
}

tasks.javadoc {
    // Silence "reference not found" - javadocs reference Java 16+ symbols
    (options as CoreJavadocOptions).addBooleanOption("Xdoclint:-reference")
}

// Add the classes of the "java16" module as a resource.
// Similar to shading, except won't cause issues with mismatching major version.
val impl = project(":java16")
tasks.processResources {
    val mainClasses = impl.sourceSets.main.flatMap { it.java.destinationDirectory }
    dependsOn(impl.tasks.assemble)
    from(mainClasses)
}
