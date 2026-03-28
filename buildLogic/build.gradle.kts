plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
  `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
  implementation(libs.plugin.dokka)
  implementation(libs.plugin.nebula.publishing)
}
