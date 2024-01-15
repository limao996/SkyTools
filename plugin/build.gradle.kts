plugins {
	kotlin("jvm")
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
	api(rootProject)
}
