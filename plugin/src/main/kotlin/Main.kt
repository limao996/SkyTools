import core.plugin.PluginBuilder

object Main : PluginBuilder() {
	override fun build() {
		buildConfig {
			version = "1.0.0-1"
			packageName = "org.skytools.core"

			introduce {
				name = "Core"
				description = "SkyTools 核心服务"
				tag("核心")
				tag("工具")
			}

			service {
				priority = Priority.Middle
				mainClass = CoreService
				targetSDK = 0
			}

			dependencies {
				api("org.skytools.core:1.0.0-1")
			}
		}
	}
}