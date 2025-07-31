package com.txstate.sampleapicall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.txstate.sampleapicall.ui.theme.FarmFreshApiCallTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FarmFreshApiCallTheme {
                FarmFreshHub()
            }
        }
    }
}

@Composable
fun showListView(modifier: Modifier = Modifier) {
    val userViewModel: MainViewModel = viewModel()
    val list by userViewModel.post.collectAsStateWithLifecycle()

    Column {
        AsyncImage(
            model = "https://placehold.co/600x400",
            contentDescription = "Image from URL",
            modifier = Modifier.size(80.dp),           // add size, padding, etc. here
            contentScale = ContentScale.Crop
        )
    LazyColumn {

        items(list) { apiList ->
            Row (modifier = modifier.fillMaxWidth()
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start){
                Text("Price : ${apiList.price}")
                Text("ID : ${apiList.id}")
                Text("Item : ${apiList.type}")
                Text("img_src : ${apiList.img_src}")
                AsyncImage(
                    model = "https://placehold.co/600x400",
                    contentDescription = "Image from URL",
                    modifier = Modifier.size(80.dp),           // add size, padding, etc. here
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FarmFreshApiCallTheme {
        showListView()
    }
}