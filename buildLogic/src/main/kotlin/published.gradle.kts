import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
  id("org.jetbrains.dokka")
  id("com.netflix.nebula.maven-publish")
  id("com.netflix.nebula.source-jar")
  signing
}

publishing {
  repositories {
    maven {
      name = "nexus"
      url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
      credentials {
        username = findProperty("ossrhToken") as String?
        password = findProperty("ossrhTokenPassword") as String?
      }
    }
  }

  publications {
    getByName<MavenPublication>("nebula") {
      pom {
        description.set("An assertion library for Kotlin")
        url.set("https://strikt.io/")
        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }
        developers {
          developer {
            id.set("robfletcher")
            name.set("Rob Fletcher")
            email.set("rob at freeside.co")
          }
        }
        inceptionYear.set("2017")
        scm {
          connection.set("scm:git:git://github.com/robfletcher/strikt.git")
          developerConnection.set("scm:git:ssh://github.com/robfletcher/strikt.git")
          url.set("http://github.com/robfletcher/strikt/")
        }
        issueManagement {
          system.set("GitHub Issues")
          url.set("https://github.com/robfletcher/strikt/issues")
        }
      }
    }
  }
}

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
  sign(publishing.publications["nebula"])
}

plugins.withId("kotlin") {
  tasks.withType<Javadoc> {
    enabled = false
  }

  tasks.withType<DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
      configureEach {
        jdkVersion.set(11)
      }
    }
  }

  val dokkaJar = tasks.register<Jar>("dokkaJar") {
    group = "build"
    description = "Assembles Javadoc jar from Dokka API docs"
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
  }

  publishing {
    publications {
      getByName<MavenPublication>("nebula") {
        artifact(dokkaJar)
      }
    }
  }
}
