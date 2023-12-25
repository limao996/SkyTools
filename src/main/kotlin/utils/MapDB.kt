package utils

import org.mapdb.DB
import org.mapdb.DBMaker
import java.io.Closeable
import java.util.concurrent.ConcurrentMap
import kotlin.reflect.KProperty

open class MapDB(private val name: String) : ConcurrentMap<String, Any?>, Closeable {
	open val autoCommit: Boolean = false

	companion object {
		private val db =
			DBMaker.fileDB("assets/data.db").closeOnJvmShutdown().checksumHeaderBypass()
				.fileChannelEnable().cleanerHackEnable().make()
		private val hookSet = HashSet<MapDB>()

		init {
			Runtime.getRuntime().addShutdownHook(Thread {
				for (entry in hookSet) entry.close()
				if (!db.isClosed()) db.close()
			})
		}
	}

	val map = DB.HashMapMaker<String, Any?>(db, name).createOrOpen()

	init {
		hookSet.add(this)
	}

	fun commit() = db.commit()

	class Node<T>(private val defaultValue: (() -> T)? = null) {

		operator fun getValue(thisRef: MapDB, property: KProperty<*>): T {
			var value = thisRef.map[property.name]
			if (value == null && defaultValue != null) {
				value = defaultValue.invoke()
				thisRef.map[property.name] = value
			}
			return value as T
		}

		operator fun setValue(thisRef: MapDB, property: KProperty<*>, value: T) {
			if (value == null) {
				thisRef.map.remove(property.name)
				return
			}
			thisRef.map[property.name] = value
			if (thisRef.autoCommit) thisRef.commit()
		}
	}

	override fun containsKey(key: String?) = map.containsKey(key)

	override fun containsValue(value: Any?) = map.containsValue(value)

	override fun get(key: String?) = map[key]

	override fun clear() = map.clear()

	override fun remove(key: String?) = map.remove(key)

	override fun remove(key: String, value: Any?) = map.remove(key, value)

	override fun putAll(from: Map<out String, Any?>) = map.putAll(from)

	override fun put(key: String?, value: Any?) = map.put(key, value)

	override fun isEmpty() = map.isEmpty()
	override fun replace(key: String?, value: Any?) = map.replace(key, value)

	override fun replace(key: String?, oldValue: Any?, newValue: Any?) =
		map.replace(key, oldValue, newValue)


	override fun putIfAbsent(key: String, value: Any?) = map.putIfAbsent(key, value)

	override val entries: MutableSet<MutableMap.MutableEntry<String?, Any?>>
		get() = map.entries
	override val keys: MutableSet<String>
		get() = map.keys
	override val values: MutableCollection<Any?>
		get() = map.values
	override val size: Int
		get() = map.size

	override fun close() {
		if (!map.isClosed()) map.close()
		hookSet.remove(this)
	}

}