
allprojects {
    apply(plugin = "java-library")
    group = "io.github.wasabithumb"
    version = "0.2.0"

    tasks.withType(JavaCompile::class) {
        // Silence "source value 8 is obsolete" - library will support java 8 for now!
        options.compilerArgs.addAll(listOf("-Xlint:-options"))

        // Silence "reference not found" - javadocs reference Java 16+ symbols despite compiling for Java 8
        options.compilerArgs.addAll(listOf("-Xdoclint:-reference"))
    }

    tasks.withType(Javadoc::class) {
        // Silence "reference not found" - javadocs reference Java 16+ symbols despite compiling for Java 8
        (options as CoreJavadocOptions).addBooleanOption("Xdoclint:-reference")
    }
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

// Add facet classes as a resource.
// Similar to shading but won't cause issues with mismatching major version.
val facets = listOf(
    project(":facets:base"),
    project(":facets:never"),
    project(":facets:caching"),
    project(":facets:invoke"),
    project(":facets:direct")
)
tasks.processResources {
    for (facet in facets) {
        dependsOn(facet.tasks.assemble)
        from(facet.sourceSets.main.flatMap { it.java.destinationDirectory })
    }
}

// Add tasks to run the benchmark
fun benchmarkSubTask(name: String, desc: String, noCache: Boolean, noDirect: Boolean): Provider<out JavaExec> {
    val benchmarkProject = project(":benchmark")
    return tasks.register(name, JavaExec::class) {
        dependsOn(benchmarkProject.tasks.assemble)
        description = desc
        javaLauncher = javaToolchains.launcherFor { languageVersion = JavaLanguageVersion.of(25) }
        mainClass = "io.github.wasabithumb.recsup.benchmark.Benchmark"
        jvmArgs = listOf(
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UnlockDiagnosticVMOptions",
            "-XX:+UseEpsilonGC",
            "-XX:+AlwaysActAsServerClassMachine",
            "-XX:+AlwaysPreTouch",
            "-XX:-DontCompileHugeMethods",
            "-Xms1G",
            "-Xmx1G",
            "-Dio.github.wasabithumb.recsup.RecordSupport.noCache=$noCache",
            "-Dio.github.wasabithumb.recsup.RecordSupport.noDirect=$noDirect",
        )
        classpath(
            benchmarkProject.java.sourceSets.main.flatMap { it.java.destinationDirectory },
            benchmarkProject.configurations.runtimeClasspath.map { it.files }
        )
        doFirst {
            logger.lifecycle("Running benchmark \"${name}\"")
        }
    }
}

val benchmarkDirectUncachedTask = benchmarkSubTask(
    "benchmarkDirectUncached",
    "Run a benchmark (direct, uncached)",
    noCache = true,
    noDirect = false
)

val benchmarkDirectCachedTask = benchmarkSubTask(
    "benchmarkDirectCached",
    "Run a benchmark (direct, cached)",
    noCache = false,
    noDirect = false
)

val benchmarkInvokeUncachedTask = benchmarkSubTask(
    "benchmarkInvokeUncached",
    "Run a benchmark (invoke, uncached)",
    noCache = true,
    noDirect = true
)

val benchmarkInvokeCachedTask = benchmarkSubTask(
    "benchmarkInvokeCached",
    "Run a benchmark (invoke, cached)",
    noCache = false,
    noDirect = true
)

tasks.register("benchmark") {
    description = "Run all benchmarks"
    dependsOn(
        benchmarkDirectCachedTask,
        benchmarkDirectUncachedTask,
        benchmarkInvokeCachedTask,
        benchmarkInvokeUncachedTask
    )
}
