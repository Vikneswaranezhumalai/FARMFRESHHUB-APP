package com.txstate.sampleapicall

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.txstate.sampleapicall.model.SignupRequest
import com.txstate.sampleapicall.screens.AddProductScreen
import com.txstate.sampleapicall.screens.DashboardScreen
import com.txstate.sampleapicall.screens.MyListingScreen
import com.txstate.sampleapicall.screens.SettingsScreen
import com.txstate.sampleapicall.screens.ViewOrderScreen
import com.txstate.sampleapicall.screens.ViewReviews


object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val DASHBOARD = "dashboard"
    const val MY_PRODUCT = "myproduct"
    const val ADD_PRODUCT = "addproduct"
    const val SETTINGS = "settings"
    const val VIEW_ORDERS = "vieworders"
    const val VIEW_REVIEW = "viewreviews"

}
@Composable
fun NavGraph(
    modifier: Modifier,
    viewModel: MainViewModel,
    navController: NavHostController
) {
    val loginState by viewModel.loginResult.collectAsState()
    val signupState by viewModel.signUpResult.collectAsState()

    NavHost(navController, startDestination = Routes.LOGIN, modifier = modifier) {
        composable(Routes.LOGIN) {
            LoginScreen(viewModel,
                loginState,
                onLogin = { email, password -> viewModel.login(email, password) },
                onNavigateToSignup = { navController.navigate("signup") },
                onNavigateToDashboard = { navController.navigate("dashboard") } // Navigate to dashboard after login
            )
        }
        composable(Routes.SIGNUP) {
            SignupScreen(viewModel,
                signupState,
                onSignup = { n, e, p, ph, rl, ad ->
                    val singup = SignupRequest(n, e, p, ph, rl, ad)
                    viewModel.signup(singup)},
                onNavigateToLogin = { navController.popBackStack() },
                onNavigateToDashboard = { navController.navigate("dashboard") }
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.MY_PRODUCT) {
            MyListingScreen(viewModel,
                onAddProductClick = { navController.navigate(Routes.ADD_PRODUCT) }
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(viewModel,onViewOrders= {navController.navigate(Routes.VIEW_ORDERS)},onViewReviews= {navController.navigate(Routes.VIEW_REVIEW)}, onLogout = { navController.navigate(
                Routes.LOGIN)},modifier =  Modifier)
        }
      composable(Routes.ADD_PRODUCT) {
          AddProductScreen()
      }

        composable(Routes.VIEW_ORDERS) {
            ViewOrderScreen(
                )
        }
        composable(Routes.VIEW_REVIEW) {
            ViewReviews(
            )
        }
    }
}