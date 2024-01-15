package core.plugin

data class PluginConfig(
	val name: String, //插件名称
	val version: String, //插件版本
	val packageName: String, //插件包名
	val description: String, //插件描述
	val priority: Int, //加载优先级
	val mainService: PluginService,//入口服务
	val tag: List<String>, //插件标签
	val targetSDK: Int, //目标SDk
	val dependencies: List<String>, //依赖列表
)