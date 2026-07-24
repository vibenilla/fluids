import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
    `java-library`
    id("com.vanniktech.maven.publish") version "0.37.0"
}

description = "Water and lava physics for Minestom"
group = "rocks.minestom"

java.toolchain.languageVersion = JavaLanguageVersion.of(25)

val minestomVersion = "2026.07.22-26.2"

mavenPublishing {
    val mcVersion = minestomVersion.split("-")[1]
    val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val version = "$date-$mcVersion"
    coordinates(project.group.toString(), project.name, version)

    publishToMavenCentral()

    if (project.hasProperty("signing.keyId") || project.hasProperty("signingInMemoryKey")) {
        signAllPublications()
    }

    pom {
        name = project.name
        description = project.description
        url = "https://github.com/vibenilla/fluids"

        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/license/mit"
            }
        }

        developers {
            developer {
                name = "mudkip"
                id = "mudkipdev"
                email = "mudkip@mudkip.dev"
                url = "https://mudkip.dev"
            }
        }

        scm {
            url = "https://github.com/vibenilla/fluids"
            connection = "scm:git:git://github.com/vibenilla/fluids.git"
            developerConnection = "scm:git:ssh://git@github.com/vibenilla/fluids.git"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.minestom:minestom:$minestomVersion")
    compileOnly("it.unimi.dsi:fastutil:8.5.18")
    testImplementation("net.minestom:minestom:$minestomVersion")
    testImplementation("net.minestom:testing:$minestomVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:6.1.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("minestom.inside-test", "true")
    failOnNoDiscoveredTests = false
}