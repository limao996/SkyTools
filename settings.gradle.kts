pluginManagement {
	repositories {
		gradlePluginPortal()
		maven("https://maven.aliyun.com/nexus/content/groups/public")
		google()
		mavenCentral()
		maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
		maven("https://www.jitpack.io")
	}

	plugins {
		kotlin("jvm").version(extra["kotlin.version"] as String)
		id("org.jetbrains.compose").version(extra["compose.version"] as String)
	}
}

rootProject.name = "SkyTools-Compose"
include("fastkv")
