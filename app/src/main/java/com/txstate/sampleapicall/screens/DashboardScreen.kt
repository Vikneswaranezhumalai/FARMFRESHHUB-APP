package com.txstate.sampleapicall.screens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

data class Category(val category_id: Int, val name: String)
data class Product(
    val product_id: Int,
    val name: String,
    val description: String,
    val available_qty: Int,
    val price_per_unit: Double,
    val unit: String,
    val category_id: Int
)
@Composable
fun DashboardScreen(onLogout: () -> Unit) {
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

    val cartItems = remember { mutableStateMapOf<Int, Int>() }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    val filteredProducts = remember(selectedCategory) {
        if (selectedCategory == null) dummyProducts
        else dummyProducts.filter { it.category_id == selectedCategory!!.category_id }
    }

    Scaffold(
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                CartSummary(cartItems, dummyProducts,
                    onClear = { cartItems.clear() },
                    onPlaceOrder = { cartItems.clear() }
                )
            }
        }
    ) { innerPadding ->
        Column(Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            Text(
                text = "Welcome to Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
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
                    ProductMarketItem(
                        product = product,
                        onPurchase = { prod, qty ->
                            cartItems[prod.product_id] =
                                (cartItems[prod.product_id] ?: 0) + qty
                        }
                    )
                }

                item { Spacer(Modifier.height(100.dp)) } // leave space for cart
            }
        }
    }
}

@Composable
fun CartSummary(
    cartItems: Map<Int, Int>,
    products: List<Product>,
    onClear: () -> Unit,
    onPlaceOrder: () -> Unit
) {
    Surface(
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shadowElevation = 6.dp,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("🛒 Cart Summary", style = MaterialTheme.typography.titleMedium)

            var total = 0.0
            cartItems.forEach { (productId, qty) ->
                val product = products.find { it.product_id == productId }
                product?.let {
                    Text("${it.name}: $qty ${it.unit} → \$${qty * it.price_per_unit}")
                    total += qty * it.price_per_unit
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Total: \$${String.format("%.2f", total)}", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                OutlinedButton(onClick = onClear) {
                    Text("Clear")
                }
                Button(onClick = onPlaceOrder) {
                    Text("Place Order")
                }
            }
        }
    }
}



@Composable
fun ProductMarketItem(
    product: Product,
    onPurchase: (Product, Int) -> Unit = { _, _ -> }
) {
    var quantity by remember { mutableStateOf(1) }

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

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Decrease")
                    }
                    Text(
                        text = quantity.toString(),
                        modifier = Modifier.width(30.dp),
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = { if (quantity < product.available_qty) quantity++ }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase")
                    }
                }

                Button(onClick = {
                    onPurchase(product, quantity)
                }) {
                    Text("Buy")
                }
            }
        }
    }
}


