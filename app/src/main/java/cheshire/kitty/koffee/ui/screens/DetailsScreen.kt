package cheshire.kitty.koffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DetailsScreen(something: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().background(Color.Yellow), Alignment.Center) {
        Text("On Details as $something")
    }
}