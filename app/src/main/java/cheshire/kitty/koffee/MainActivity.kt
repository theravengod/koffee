package cheshire.kitty.koffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cheshire.kitty.koffee.ui.navi.AppNavigation
import cheshire.kitty.koffee.ui.theme.KoffeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoffeeTheme { AppNavigation() }
        }
    }
}
