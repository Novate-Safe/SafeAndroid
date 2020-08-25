package net.novate.base.timber

import android.content.Context
import androidx.annotation.Keep
import androidx.startup.Initializer
import net.novate.base.BuildConfig
import timber.log.Timber

/**
 * Timber 初始化器
 */
@Keep
class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}