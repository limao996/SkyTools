package core

import core.plugin.PluginService

abstract class Workspace : Service {
	lateinit var context: PluginService
	abstract fun onStart()
	abstract fun onPause()
	abstract fun onStop()
}
