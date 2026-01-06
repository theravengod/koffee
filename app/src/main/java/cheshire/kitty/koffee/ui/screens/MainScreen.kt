package cheshire.kitty.koffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cheshire.kitty.koffee.ui.theme.KoffeeTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier, onNav: (something: String) -> Unit) {
    MainScreenContent(modifier) { action -> onNav((action as MainScreenAction.GoToNextScreen).data) }
}

@Composable
fun MainScreenContent(modifier: Modifier = Modifier, onAction: (MainScreenAction) -> Unit) {
    var something by rememberSaveable { mutableStateOf("") }
    Scaffold(modifier = modifier) { pv ->
        Box(modifier
            .padding(pv)
            .fillMaxSize()
            .background(Color.Green), Alignment.Center) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Kpu la peshte !")
                TextField(something, onValueChange = { something = it }, label = { Text("Put something here") })
                Button(onClick = { onAction(MainScreenAction.GoToNextScreen(something)) }) {
                    Text("Go there")
                }
            }
        }
    }
}

sealed class MainScreenAction {
    data class GoToNextScreen(val data: String) : MainScreenAction()
}

@Preview
@Composable
private fun Preview_MainScreenContent() = KoffeeTheme {
    MainScreenContent { }
}