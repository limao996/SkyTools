package core.plugin

import sdkCode

abstract class PluginBuilder {
	val configList = ArrayList<PluginConfig>()
	abstract fun build()

	fun buildConfig(fn: BuildConfigScope.() -> Unit) {
		val scope = BuildConfigScope()
		scope.fn()
		configList.add(
			PluginConfig(
				scope.name,
				scope.version,
				scope.packageName,
				scope.description,
				scope.priority.priority,
				scope.mainService,
				scope.tag,
				scope.targetSDK,
				scope.dependencies,
			)
		)
	}

	class BuildConfigScope {
		lateinit var name: String //插件名称
		lateinit var version: String //插件版本
		lateinit var packageName: String //插件包名
		lateinit var description: String //插件描述
		var priority: Priority = Priority.Middle //加载优先级
		lateinit var mainService: PluginService//入口服务
		lateinit var tag: List<String> //插件标签
		var targetSDK: Int = sdkCode//目标SDk
		lateinit var dependencies: List<String> //依赖列表


		fun introduce(fn: IntroduceScope.() -> Unit) {
			val scope = IntroduceScope()
			scope.fn()
			name = scope.name
			description = scope.description
			tag = scope.tagList
		}

		fun service(fn: ServiceScope.() -> Unit) {
			val scope = ServiceScope()
			scope.fn()
			priority = scope.priority
			mainService = scope.mainClass
			targetSDK = scope.targetSDK
		}

		fun dependencies(fn: DependenciesScope.() -> Unit) {
			val scope = DependenciesScope()
			scope.fn()
			dependencies = scope.dependencies
		}
	}

	enum class Priority(val priority: Int) {
		High(-1), Middle(0), Low(1)
	}

	class IntroduceScope {
		lateinit var name: String //插件名称
		lateinit var description: String //插件描述
		val tagList = ArrayList<String>()//插件标签
		fun tag(name: String) {
			tagList.add(name)
		}
	}

	class ServiceScope {
		var priority: Priority = Priority.Middle //加载优先级
		lateinit var mainClass: PluginService //入口服务
		var targetSDK: Int = sdkCode //目标SDk
	}

	class DependenciesScope {
		val dependencies = ArrayList<String>()
		fun api(uri: String) {
			dependencies.add(uri)
		}
	}
}
