package cheshire.kitty.koffee.ui.navi

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import cheshire.kitty.koffee.ui.screens.DetailsScreen
import cheshire.kitty.koffee.ui.screens.MainScreen
import kotlinx.serialization.Serializable

@Serializable
data object NDMainScreen: NavKey

@Serializable
data class NDDetailsScreen(val something: String): NavKey

fun destinations(backStack: NavBackStack<NavKey>): (NavKey) -> NavEntry<NavKey> = entryProvider {
    entry<NDMainScreen> { MainScreen { backStack.add(NDDetailsScreen(it)) } }
    entry<NDDetailsScreen> { DetailsScreen(it.something) }
}
