import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("java-library")
    alias(libs.plugins.publish)
}

group = "io.github.wasabithumb"
version = "0.1.0"
description = "Java 8 library for reading Java 14+ records"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.annotations)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    // Toolchain is standardized here; JDK 17 is the oldest LTS which can build
    // both the main Java 8 code and the Java 16 stub.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

// Important to allow test source to use the record keyword
tasks.compileTestJava {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}

tasks.javadoc {
    options.encoding = Charsets.UTF_8.name()
    (options as CoreJavadocOptions).addBooleanOption("Xdoclint:none", true)
}

tasks.test {
    useJUnitPlatform()
}

// Add the classes of the "java16" module as a resource.
// Similar to shading, except won't cause issues with mismatching major version
tasks.processResources {
    val impl = project(":java16")
    dependsOn(impl.tasks.assemble)
    from(impl.layout.buildDirectory.dir("classes/java/main"))
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    coordinates("${project.group}", "recsup", "${project.version}")
    pom {
        name.set("RecSup")
        description.set(project.description!!)
        inceptionYear.set("2025")
        url.set("https://github.com/WasabiThumb/recsup")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("wasabithumb")
                name.set("Xavier Pedraza")
                url.set("https://github.com/WasabiThumb/")
            }
        }
        scm {
            url.set("https://github.com/WasabiThumb/recsup/")
            connection.set("scm:git:git://github.com/WasabiThumb/recsup.git")
        }
    }
}
