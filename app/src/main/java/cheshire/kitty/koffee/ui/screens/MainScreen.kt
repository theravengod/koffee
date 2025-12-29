package cheshire.kitty.koffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(modifier: Modifier = Modifier, onNav: (something: String) -> Unit) {
    var something by rememberSaveable { mutableStateOf("") }
    Scaffold(modifier = modifier) { pv ->
        Box(modifier.padding(pv).fillMaxSize().background(Color.Green), Alignment.Center) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Kpu la peshte !")
                TextField(something, onValueChange = { something = it }, label = { Text("Put something here") })
                Button(onClick = { onNav(something) }) {
                    Text("Go there")
                }
            }
        }
    }
}