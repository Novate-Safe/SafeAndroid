package net.novate.base.extension

import net.novate.base.tuple.Single
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * 扩展属性映射表，映射对象和其额外属性表
 */
private val EXTENSIONS by lazy {
    ConcurrentWeakIdentityHashMap<Any, MutableMap<String, Single<Any?>>>()
}

/**
 * 设置属性
 */
fun Any.setExtension(
    name: String,
    value: Any?,
    extensions: MutableMap<String, Single<Any?>> = EXTENSIONS.getOrPut(this) { ConcurrentHashMap() }
) {
    extensions[name] = Single(value)
}

/**
 * 获取属性
 */
@Suppress("UNCHECKED_CAST")
fun <T> Any.getExtension(
    name: String,
    extensions: MutableMap<String, Single<Any?>> = EXTENSIONS.getOrPut(this) { ConcurrentHashMap() },
    default: () -> T
): T {
    return extensions.getOrPut(name) { Single(default()) }.value as T
}

/**
 * Concurrent Weak Identity HashMap
 */
private class ConcurrentWeakIdentityHashMap<K, V>(initialCapacity: Int = 16) : AbstractMutableMap<K, V>(), ConcurrentMap<K, V> {

    private val map = ConcurrentHashMap<WeakIdentityKey<K>, V>(initialCapacity)
    private val queue = ReferenceQueue<K>()
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> = Entries()

    override val size: Int
        get() = purgeKeys().let { map.size }

    override fun put(key: K, value: V): V? = purgeKeys().let { map.put(WeakIdentityKey(key, queue), value) }

    override fun putIfAbsent(key: K, value: V): V? = purgeKeys().let { map.putIfAbsent(WeakIdentityKey(key, queue), value) }

    override fun get(key: K): V? = purgeKeys().let { map[WeakIdentityKey(key)] }

    override fun remove(key: K): V? = purgeKeys().let { map.remove(WeakIdentityKey(key)) }

    override fun remove(key: K, value: V): Boolean = purgeKeys().let { map.remove(WeakIdentityKey(key), value) }

    override fun replace(key: K, value: V): V? = purgeKeys().let { map.replace(WeakIdentityKey(key), value) }

    override fun replace(key: K, oldValue: V, newValue: V): Boolean = purgeKeys().let { map.replace(WeakIdentityKey(key), oldValue, newValue) }

    override fun containsKey(key: K): Boolean = purgeKeys().let { map.containsKey(WeakIdentityKey(key)) }

    override fun containsValue(value: V): Boolean = purgeKeys().let { map.containsValue(value) }

    @Suppress("ControlFlowWithEmptyBody")
    override fun clear() {
        while (queue.poll() != null);
        map.clear()
    }

    @Suppress("ControlFlowWithEmptyBody")
    private fun purgeKeys() {
        while (queue.poll()?.also { map.remove(it) } != null);
    }

    private inner class Entries : AbstractMutableSet<MutableMap.MutableEntry<K, V>>() {
        override val size: Int = this@ConcurrentWeakIdentityHashMap.size

        override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> {
            return Iterator(map.entries.iterator())
        }

        override fun add(element: MutableMap.MutableEntry<K, V>): Boolean {
            return this@ConcurrentWeakIdentityHashMap.put(element.key, element.value) == null
        }

        override fun remove(element: MutableMap.MutableEntry<K, V>): Boolean {
            return this@ConcurrentWeakIdentityHashMap.remove(element.key, element.value)
        }

        override fun contains(element: MutableMap.MutableEntry<K, V>): Boolean {
            return this@ConcurrentWeakIdentityHashMap[element.key] == element.value
        }

        override fun clear() {
            this@ConcurrentWeakIdentityHashMap.clear()
        }
    }

    private inner class Iterator(val iterator: MutableIterator<MutableMap.MutableEntry<WeakIdentityKey<K>, V>>) : MutableIterator<MutableMap.MutableEntry<K, V>> {

        private var nextValue: MutableMap.MutableEntry<K, V>? = null

        override fun hasNext(): Boolean {
            if (nextValue != null) {
                return true
            } else {
                while (iterator.hasNext()) {
                    val entry = iterator.next()
                    val key = entry.key.get()
                    if (key == null) {
                        iterator.remove()
                    } else {
                        nextValue = Entry(key, entry.value)
                        return true
                    }
                }
            }
            return false
        }

        override fun next(): MutableMap.MutableEntry<K, V> {
            return if (hasNext()) {
                nextValue!!.also { nextValue = null }
            } else {
                throw NoSuchElementException()
            }
        }

        override fun remove() = iterator.remove().also { nextValue = null }
    }

    private inner class Entry(override val key: K, override var value: V) : MutableMap.MutableEntry<K, V> {

        override fun setValue(newValue: V): V {
            this@ConcurrentWeakIdentityHashMap[key] = newValue
            return value.also { value = newValue }
        }

        override fun equals(other: Any?): Boolean {
            return other is MutableMap.MutableEntry<*, *> && key === other.key && value === other.value
        }

        override fun hashCode(): Int {
            return System.identityHashCode(key) xor System.identityHashCode(value)
        }

    }
}

private class WeakIdentityKey<K>(key: K, queue: ReferenceQueue<K>? = null) : WeakReference<K>(key, queue) {
    private val hash = System.identityHashCode(key)

    override fun equals(other: Any?): Boolean {
        return this === other || other is WeakIdentityKey<*> && other.get() === get()
    }

    override fun hashCode(): Int = hash
}
