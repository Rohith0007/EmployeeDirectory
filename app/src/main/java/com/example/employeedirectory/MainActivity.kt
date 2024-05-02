package com.example.employeedirectory

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.disk.DiskCache
import okhttp3.OkHttpClient
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.employeedirectory.ui.theme.EmployeeDirectoryTheme
import viewmodel.EmployeeViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import model.Employee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import coil.Coil
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageLoader = ImageLoader.Builder(applicationContext)
            .memoryCache {
                MemoryCache.Builder(applicationContext)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(applicationContext.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .okHttpClient {
                OkHttpClient.Builder()
                    .build()
            }
            .build()

        Coil.setImageLoader(imageLoader)
        setContent {
            EmployeeDirectoryTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    EmployeeList()
                }
            }
        }
    }
}

@Composable
fun EmployeeList(viewModel: EmployeeViewModel = viewModel()) {
    val triggerRefresh = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = triggerRefresh.value) {
        viewModel.fetchEmployees()
    }

    val employees by viewModel.employees.observeAsState(initial = emptyList())
    val isRefreshing by viewModel.isLoading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            triggerRefresh.value = !triggerRefresh.value
        }
    ) {
        if (isRefreshing && employees.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (!error.isNullOrEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error", style = MaterialTheme.typography.bodyLarge)
            }
        } else if (employees.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No employees found", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(employees) { employee ->
                    EmployeeItem(employee)
                }
            }
        }
    }
}

@Composable
fun EmployeeItem(employee: Employee) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AndroidView(
                modifier = Modifier.size(40.dp),
                factory = { ctx ->
                    ImageView(ctx).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        )
                        Glide.with(ctx)
                            .load(employee.photo_url_small)
                            .placeholder(android.R.drawable.ic_menu_report_image)
                            .error(android.R.drawable.ic_menu_report_image)
                            .override(300) // Using a reasonable fixed size
                            .into(this)
                    }
                }
            )
            Spacer(Modifier.width(16.dp))
            Column {
                // Displaying employee's name
                Text(
                    text = "Name: ${employee.full_name}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Email: ${employee.email_address}",
                    style = MaterialTheme.typography.bodySmall
                )
                employee.phone_number?.let {
                    Text(
                        text = "Phone: $it",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                employee.biography?.let {
                    Text(
                        text = "Bio: $it",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDetailScreen(employee: Employee) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Employee Details") })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(
                    data = employee.photo_url_large,
                    builder = {
                        placeholder(android.R.drawable.ic_menu_report_image)
                        error(android.R.drawable.ic_menu_report_image)
                    }
                ),
                contentDescription = "Large Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Name: ${employee.full_name}", style = MaterialTheme.typography.titleLarge)
            Text("Email: ${employee.email_address}", style = MaterialTheme.typography.bodyLarge)
            employee.phone_number?.let {
                Text("Phone: $it", style = MaterialTheme.typography.bodyLarge)
            }
            employee.biography?.let {
                Text("Bio: $it", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmployeeDetailScreen() {
    val exampleEmployee = Employee("uuid", "John Doe", "555-1234", "john@example.com", "A developer.", "url_small", "url_large", "Development", "FULL_TIME")
    EmployeeDetailScreen(exampleEmployee)
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EmployeeDirectoryTheme {
        Greeting("Android")
    }
}