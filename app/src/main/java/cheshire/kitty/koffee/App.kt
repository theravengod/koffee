package cheshire.kitty.koffee

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initStuff()
    }

    private fun initStuff() {
        if (BuildConfig.DEBUG) {
            plant(DebugTree())
        }
    }
}