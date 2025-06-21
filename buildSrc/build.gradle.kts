plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
  `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
  implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20")
  implementation("com.netflix.nebula:nebula-publishing-plugin:21.0.0")
}
