package com.txstate.sampleapicall.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.txstate.sampleapicall.MainViewModel

@Composable
fun MyListingScreen(viewModel: MainViewModel,
    onAddProductClick: () -> Unit // 🔥 Callback for Add Product Button
) {
    val dummyCategories = listOf(
        Category(1, "Vegetables"), Category(2, "Fruits"), Category(3, "Grains"),
        Category(4, "Dairy"), Category(5, "Meat"), Category(6, "Spices"), Category(7, "Herbs")
    )

    val dummyProducts = listOf(
        Product(1, "Tomato", "Fresh red tomatoes", 50, 2.0, "kg", 1),
        Product(2, "Apple", "Sweet apples", 30, 3.0, "kg", 2),
        Product(3, "Rice", "Basmati rice", 100, 1.5, "kg", 3),
        Product(4, "Milk", "Organic cow milk", 20, 1.2, "L", 4),
        Product(5, "Chicken", "Farm chicken", 15, 5.0, "kg", 5),
        Product(6, "Turmeric", "Pure ground turmeric", 40, 2.5, "kg", 6),
        Product(7, "Mint", "Fresh mint leaves", 10, 1.0, "bunch", 7),
    )

    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    val filteredProducts = remember(selectedCategory) {
        if (selectedCategory == null) dummyProducts
        else dummyProducts.filter { it.category_id == selectedCategory!!.category_id }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProductClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text(
                text = "Welcome to My Product Listing",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Category Chips
                item {
                    LazyRow {
                        item {
                            FilterChip(
                                selected = selectedCategory == null,
                                onClick = { selectedCategory = null },
                                label = { Text("All") },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }

                        items(dummyCategories.take(6)) { category ->
                            FilterChip(
                                selected = selectedCategory?.category_id == category.category_id,
                                onClick = { selectedCategory = category },
                                label = { Text(category.name) },
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }

                // Product List
                items(filteredProducts) { product ->
                    ProductMarketItem(product = product)
                }

                item { Spacer(Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
fun ProductMarketItem(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Text(product.description, style = MaterialTheme.typography.bodySmall)
            Text("Available: ${product.available_qty} ${product.unit}")
            Text("Price: $${product.price_per_unit}/${product.unit}")
        }
    }
}
