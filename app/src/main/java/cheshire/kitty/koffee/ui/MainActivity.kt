package cheshire.kitty.koffee.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cheshire.kitty.koffee.ui.navi.AppNavigation
import cheshire.kitty.koffee.ui.theme.KoffeeTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Start")
        enableEdgeToEdge()
        setContent {
            KoffeeTheme { AppNavigation() }
        }
    }
}