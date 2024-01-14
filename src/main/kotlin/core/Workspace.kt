package core

abstract class Workspace : Service {
	lateinit var context: Plugin
	abstract fun onStart()
	abstract fun onPause()
	abstract fun onStop()
}
