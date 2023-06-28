
plugins {
  application
  kotlin("jvm") version "1.8.22"
  kotlin("plugin.serialization") version "1.8.22"
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
  mavenCentral()
  maven("https://oss.sonatype.org/content/repositories/snapshots")
  maven("https://repository.jetbrains.com/all")
}

dependencies {
  implementation("org.wasabi:wasabi:0.1-SNAPSHOT")
  implementation(platform("org.http4k:http4k-bom:5.2.0.0"))
  implementation("org.http4k:http4k-core")
  implementation("org.http4k:http4k-server-undertow")
  implementation("org.http4k:http4k-server-jetty")

  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

application {
  mainClass.set("poker.PlayerServiceKt")
}

tasks.wrapper {
//  distributionType = Wrapper.DistributionType.ALL
  gradleVersion = "8.1.1"
}

val stage by tasks.registering {
  dependsOn(tasks.installShadowDist)
  group = "build"
}

