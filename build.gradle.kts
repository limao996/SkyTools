import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
	kotlin("jvm")
	id("org.jetbrains.compose")
}

group = "org.skytools"
version = "1.0-SNAPSHOT"

repositories {
	google()
	mavenCentral()
	maven("https://maven.aliyun.com/nexus/content/groups/public")
	maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
	maven("https://packages.jetbrains.team/maven/p/kpm/public/")
}

dependencies {
	api(compose.desktop.currentOs) {
		exclude("org.jetbrains.compose.material")
	}
	api("com.bybutter.compose:compose-jetbrains-expui-theme:2.1.0")
	api("org.mapdb:mapdb:3.0.10")
}

compose.desktop {
	application {
		mainClass = "MainKt"

		nativeDistributions {
			modules("jdk.unsupported")
			targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb)
			packageName = "SkyTools-Compose"
			packageVersion = "1.0.0"
		}
	}
}
