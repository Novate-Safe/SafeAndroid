package net.novate.base.timber

import net.novate.base.BuildConfig
import net.novate.base.base.BaseInitializer
import timber.log.Timber

/**
 * Timber 初始化器
 */
class TimberInitializer : BaseInitializer() {

    override fun onInit() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}