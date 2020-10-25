package net.novate.base.kotlin

/**
 * 非线程安全的延迟初始化，比 lazy 性能略高
 */
inline fun <reified T> unsafeLazy(noinline initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)