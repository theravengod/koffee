package cheshire.kitty.koffee.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cheshire.kitty.koffee.models.Product
import cheshire.kitty.koffee.ui.theme.KoffeeTheme
import cheshire.kitty.koffee.ui.vms.MainViewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier, onNav: (something: String) -> Unit) {
    val viewModel: MainViewModel = hiltViewModel<MainViewModel>()

    val products by viewModel.products.collectAsStateWithLifecycle()
    MainScreenContent(products, modifier) { action -> onNav((action as MainScreenAction.GoToNextScreen).data) }
}

@Composable
fun MainScreenContent(products: List<Product>, modifier: Modifier = Modifier, onAction: (MainScreenAction) -> Unit) {
    Scaffold(modifier = modifier) { pv ->
        Column(modifier
            .padding(pv)
            .fillMaxSize()
        ) {
            if (products.isNotEmpty()) {
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(products, key = { it.id }) { product ->
                        ProductItem(product) { onAction(MainScreenAction.GoToNextScreen(product.name ?: "No name")) }
                    }
                }
            } else {
                Text("Nothing received", Modifier.fillMaxSize(), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, modifier: Modifier = Modifier, onClick: () -> Unit) {
    product.name?.takeIf { it.isNotBlank() }?.let {
        Card(onClick, modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp), Arrangement.spacedBy(8.dp), Alignment.Start
            ) {
                Text(it, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Text(product.category ?: "No category", style = MaterialTheme.typography.bodyMedium)
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
    MainScreenContent(emptyList()) { }
}