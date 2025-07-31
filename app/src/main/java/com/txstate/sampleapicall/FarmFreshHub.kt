package com.txstate.sampleapicall

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.txstate.sampleapicall.model.AuthResponse
import com.txstate.sampleapicall.model.SignupRequest
import com.txstate.sampleapicall.repository.ApiResult
import com.txstate.sampleapicall.ui.theme.FarmFreshApiCallTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import com.txstate.sampleapicall.screens.AddProductScreen
import com.txstate.sampleapicall.screens.BottomBar
import com.txstate.sampleapicall.screens.DashboardScreen
import com.txstate.sampleapicall.screens.MyListingScreen

@Composable
fun FarmFreshHub(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = when (currentRoute) {
        Routes.DASHBOARD,
        Routes.SETTINGS,
        Routes.MY_PRODUCT -> true
        else -> false
    }
    val showTopBar = when(currentRoute){
        Routes.LOGIN,
        Routes.SIGNUP->false
        else->true
    }

    FarmFreshApiCallTheme {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                if (showTopBar) {
                    TopBar(
                        title = "FARM FRESH HUB")
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    BottomBar(navController)
                }
            },
            content = { innerPadding ->
                NavGraph(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel,
                    navController = navController
                )
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


@Composable
fun LoginScreen(viewModel: MainViewModel,
    loginState: ApiResult<AuthResponse>,
    onLogin: (String, String) -> Unit,
    onNavigateToSignup: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    var email by remember { mutableStateOf("alice@farm.com") }
    var password by remember { mutableStateOf("hash1") }
    val context = LocalContext.current

    LaunchedEffect(loginState) {
        when (loginState) {
            is ApiResult.Error -> {
                Toast.makeText(context, loginState.message, Toast.LENGTH_SHORT).show()
            }
            is ApiResult.Success -> {
                viewModel.saveUserInformation(loginState.data.user, context)
                onNavigateToDashboard()
            }
            else -> Unit
        }
    }
    // Optional: background gradient for freshness!
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF81C784), Color(0xFFE0F7FA))
                )
            )
    ) {
        // Card to contain login form
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            elevation = 12.dp,
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "FARMFRESH Hub",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF388E3C),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    "Sign in to continue",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF757575),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { onLogin(email.trim(), password.trim()) },
                    //onClick = { onNavigateToDashboard() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Login", style = MaterialTheme.typography.titleMedium)
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                TextButton(
                    onClick = onNavigateToSignup,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Don't have an account? Sign up",
                        color = Color(0xFF388E3C),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}


@Composable
fun RoleRadioGroup(
    roles: List<String>,
    selectedRole: String,
    onRoleSelected: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            "Role: ",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(end = 6.dp)
        )

        roles.forEach { role ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                RadioButton(
                    selected = role == selectedRole,
                    onClick = { onRoleSelected(role) }
                )
                Text(text = role)
            }
        }
    }
}






@Composable
fun SignupScreen(viewModel: MainViewModel,
    singupResult: ApiResult<AuthResponse>,
    onSignup: (String, String, String, String?,String?, String?) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("User") }
    val roles = listOf("User", "Former")
    val context = LocalContext.current

    LaunchedEffect(singupResult) {
        when (singupResult) {
            is ApiResult.Error -> {
                Toast.makeText(context, singupResult.message, Toast.LENGTH_SHORT).show()
            }
            is ApiResult.Success -> {
                viewModel.saveUserInformation(singupResult.data.user, context)
                onNavigateToDashboard()
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF81C784), Color(0xFFE0F7FA))
                )
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center),
            elevation = 12.dp,
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "FARMFRESH Hub",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF388E3C),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    "Create your account",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF757575),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone ") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address (optional)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                RoleRadioGroup(
                    roles = roles,
                    selectedRole = selectedRole,
                    onRoleSelected = { selectedRole = it }
                )

                Button(
                    onClick = { onSignup(
                        name.trim(),
                        email.trim(),
                        password.trim(),
                        phone.trim().ifEmpty { null },
                        selectedRole.trim(),
                        address.trim().ifEmpty { null }
                    ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sign Up", style = MaterialTheme.typography.titleMedium)
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                TextButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Already have an account? Login",
                        color = Color(0xFF388E3C),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}








//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    FarmFreshApiCallTheme {
//        val navController = rememberNavController()
////        LoginScreen( onLogin = { email, password -> },
////            onNavigateToSignup = { navController.navigate("signup") },
////            onNavigateToDashboard = { navController.navigate("signup") })
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun SignupScreenPreview() {
//    val navController = rememberNavController()
//    SignupScreen( onSignup = { n, e, p, ph,rl, ad ->},
//        onNavigateToLogin = { navController.popBackStack() })
//}

