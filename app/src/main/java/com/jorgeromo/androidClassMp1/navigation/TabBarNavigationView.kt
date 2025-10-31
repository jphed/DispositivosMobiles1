package com.jorgeromo.androidClassMp1.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.jorgeromo.androidClassMp1.firstpartial.FirstPartialView
import com.jorgeromo.androidClassMp1.firstpartial.login.views.HomeView
import com.jorgeromo.androidClassMp1.firstpartial.login.views.LoginView
import com.jorgeromo.androidClassMp1.firstpartial.lottieAnimation.LottieAnimationView
import com.jorgeromo.androidClassMp1.ids.IdsView
import com.jorgeromo.androidClassMp1.ids.imc.views.IMCView
import com.jorgeromo.androidClassMp1.ids.location.views.LocationListScreen
import com.jorgeromo.androidClassMp1.ids.login.views.LoginOptionsView
import com.jorgeromo.androidClassMp1.ids.student.views.StudentView
import com.jorgeromo.androidClassMp1.ids.sum.views.SumView
import com.jorgeromo.androidClassMp1.ids.temperature.views.TempView
import com.jorgeromo.androidClassMp1.secondpartial.SecondPartialView
import com.jorgeromo.androidClassMp1.secondpartial.home.model.network.HomeApi
import com.jorgeromo.androidClassMp1.secondpartial.home.repository.HomeRepository
import com.jorgeromo.androidClassMp1.secondpartial.home.viewmodel.HomeViewModel
import com.jorgeromo.androidClassMp1.secondpartial.home.viewmodel.HomeViewModelFactory
import com.jorgeromo.androidClassMp1.secondpartial.home.views.HomeViewRoutines
import com.jorgeromo.androidClassMp1.secondpartial.location.LocationCoordianteView
import com.jorgeromo.androidClassMp1.secondpartial.qrcode.QrCodeView
import com.jorgeromo.androidClassMp1.thirdpartial.ThirdPartialView
import com.jorgeromo.androidClassMp1.thirdpartial.ui.UITestingScreen
import com.jorgeromo.androidClassMp1.ui.thirdpartial.views.DesignSystemView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarNavigationView(navController: NavHostController = rememberNavController()) {
    val items = listOf(
        ScreenNavigation.Ids,
        ScreenNavigation.FirstPartial,
        ScreenNavigation.SecondPartial,
        ScreenNavigation.ThirdPartial
    )

    // Mapa de títulos por ruta
    val routeTitles = remember {
        mapOf(
            ScreenNavigation.Ids.route to ScreenNavigation.Ids.label,
            ScreenNavigation.FirstPartial.route to ScreenNavigation.FirstPartial.label,
            ScreenNavigation.SecondPartial.route to ScreenNavigation.SecondPartial.label,
            ScreenNavigation.ThirdPartial.route to ScreenNavigation.ThirdPartial.label,

            // Rutas internas
            ScreenNavigation.IMC.route to ScreenNavigation.IMC.label,
            ScreenNavigation.Login.route to ScreenNavigation.Login.label,
            ScreenNavigation.LoginOptions.route to ScreenNavigation.LoginOptions.label,
            ScreenNavigation.Sum.route to ScreenNavigation.Sum.label,
            ScreenNavigation.Temperature.route to ScreenNavigation.Temperature.label,
            ScreenNavigation.StudentList.route to ScreenNavigation.StudentList.label,
            ScreenNavigation.Locations.route to ScreenNavigation.Locations.label,
            ScreenNavigation.SecondHome.route to ScreenNavigation.SecondHome.label,
            ScreenNavigation.DesignSystem.route to ScreenNavigation.DesignSystem.label,

            // 🔹 NUEVA ruta Lottie
            "LottieRoute" to "Animación Lottie"
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentTitle = routeTitles[currentRoute] ?: ""

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Jorge Parra 13104") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF9575CD),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { screen ->
                    val selected = currentRoute == screen.route
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenNavigation.Ids.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenNavigation.Ids.route) { IdsView(navController) }
            composable(ScreenNavigation.FirstPartial.route) { FirstPartialView(navController) }
            composable(ScreenNavigation.SecondPartial.route) { SecondPartialView(navController) }
            composable(ScreenNavigation.ThirdPartial.route) { ThirdPartialView(navController) }

            // Rutas internas
            composable(ScreenNavigation.Home.route) { HomeView() }
            composable(ScreenNavigation.IMC.route) { IMCView() }
            composable(ScreenNavigation.Login.route) { LoginView(navController) }
            composable(ScreenNavigation.LoginOptions.route) { LoginOptionsView(navController) }
            composable(ScreenNavigation.Sum.route) { SumView() }
            composable(ScreenNavigation.Temperature.route) { TempView() }
            composable(ScreenNavigation.StudentList.route) { StudentView() }
            composable(ScreenNavigation.Locations.route) { LocationListScreen() }
            composable(ScreenNavigation.SecondHome.route) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gist.githubusercontent.com/YajahiraPP/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val api = retrofit.create(HomeApi::class.java)
                val repo = HomeRepository(api)
                val vm: HomeViewModel = viewModel(factory = HomeViewModelFactory(repo))
                val uiState by vm.ui.collectAsState()
                LaunchedEffect(Unit) { vm.fetchHome() }
                HomeViewRoutines(uiState = uiState)
            }

            // Ruta para lector de QR
            composable(ScreenNavigation.QrCode.route) { QrCodeView() }

            // Nueva ruta para coordenadas en vivo
            composable(ScreenNavigation.LocationCoordinate.route) { LocationCoordianteView() }

            // Ruta para Sistema de Diseño
            composable(ScreenNavigation.DesignSystem.route) { DesignSystemView() }

            // Ruta para Pruebas de UI
            composable(ScreenNavigation.UITesting.route) { backStackEntry ->
                val navController = rememberNavController()
                UITestingScreen(
                    onRunTests = {
                        // Aquí podrías lanzar las pruebas programáticamente si fuera necesario
                    },
                    navController = navController
                )
            }

            // 🔹 NUEVA ruta para Lottie
            composable("LottieRoute") { LottieAnimationView(navController) }
        }
    }
}
