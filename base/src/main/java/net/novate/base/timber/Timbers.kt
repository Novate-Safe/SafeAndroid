@file:Suppress("NOTHING_TO_INLINE")

package net.novate.base.timber

import timber.log.Timber

inline fun logV(message: String? = null, throwable: Throwable? = null) = Timber.v(throwable, message)
inline fun logD(message: String? = null, throwable: Throwable? = null) = Timber.d(throwable, message)
inline fun logI(message: String? = null, throwable: Throwable? = null) = Timber.i(throwable, message)
inline fun logW(message: String? = null, throwable: Throwable? = null) = Timber.w(throwable, message)
inline fun logE(message: String? = null, throwable: Throwable? = null) = Timber.e(throwable, message)
inline fun wtf(message: String? = null, throwable: Throwable? = null) = Timber.wtf(throwable, message)
inline fun log(priority: Int, message: String? = null, throwable: Throwable? = null) = Timber.log(priority, throwable, message)