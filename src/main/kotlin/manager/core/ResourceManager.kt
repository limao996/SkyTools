package manager.core

import java.io.File

object ResourceManager {
	fun init() {
		File("assets").apply {
			if (!exists()) {
				mkdir()
			}
		}
	}
}