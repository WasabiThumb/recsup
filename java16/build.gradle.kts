
plugins {
    id("java-library")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.annotations)
    compileOnly(rootProject)
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.javadoc {
    options.encoding = Charsets.UTF_8.name()
    (options as CoreJavadocOptions).addBooleanOption("Xdoclint:none", true)
}
