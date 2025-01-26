package com.example.pokemondirectory.view

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokemondirectory.model.repository.data.PokemonItem
import com.example.pokemondirectory.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonNavHost()
        }
    }
}

@Composable
fun PokemonNavHost() {
    val navController = rememberNavController()
    val viewModel:PokemonViewModel = hiltViewModel()
    NavHost(navController, startDestination = "list") {
        composable("list") {
            PokemonItemListScreen(viewModel) { detailsUrl ->
                navController.navigate("details/${Uri.encode(detailsUrl)}")
            }
        }

        composable(
            route = "details/{detailsUrl}",
            arguments = listOf(navArgument("detailsUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val detailsUrl = Uri.decode(backStackEntry.arguments?.getString("detailsUrl"))
            PokemonItemDetailScreen(viewModel, detailsUrl)
        }
    }
}

@Composable
fun PokemonItemListScreen(viewModel: PokemonViewModel, onItemClick: (String) -> Unit) {
    val lazyPagingItems = viewModel.pokemonItems.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyPagingItems.itemCount) { itemIndex ->
            lazyPagingItems[itemIndex]?.let {
                PokemonItemRow(it) { detailsUrl ->
                    onItemClick(detailsUrl)
                }
            }
        }
        lazyPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        Text(text = "Error loading more data", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItemDetailScreen(viewModel: PokemonViewModel, detailsUrl: String) {
    // Trigger fetch of Pokemon details
    LaunchedEffect(detailsUrl) {
        viewModel.fetchPokemonDetails(detailsUrl)
    }

    // Observe the details state
    val detailsState by viewModel.pokemonDetails.collectAsState(initial = null)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        detailsState?.let { details ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display Name
                Card(
                    elevation = CardDefaults.cardElevation(), // Slight elevation for a material card effect
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Name: ${details.name}",
                            style = MaterialTheme.typography.headlineMedium, // Body1 for list items
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Card(
                    elevation = CardDefaults.cardElevation(), // Slight elevation for a material card effect
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Height: ${details.height}",
                            style = MaterialTheme.typography.headlineMedium, // Body1 for list items
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                // Display Pokemon image
                AndroidView(
                    factory = { context ->
                        ImageView(context).apply { scaleType = ImageView.ScaleType.CENTER_CROP }
                    },
                    update = { imageView ->
                        Glide.with(imageView.context)
                            .load(details.sprites.frontDefault)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView)
                    },
                    modifier = Modifier
                        .size(400.dp)
                        .padding(16.dp).align(Alignment.CenterHorizontally),
                )


            }
        } ?: CircularProgressIndicator() // Show loading spinner if data is not yet available
    }
}

@Composable
fun PokemonItemRow(pokemonItem: PokemonItem, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(pokemonItem.url) } // Pass the detailsUrl on click
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(), // Slight elevation for a material card effect
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = pokemonItem.name,
                style = MaterialTheme.typography.headlineMedium, // Body1 for list items
                modifier = Modifier.weight(1f)
            )

            // Right-aligned chevron icon to indicate more info
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Details",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
