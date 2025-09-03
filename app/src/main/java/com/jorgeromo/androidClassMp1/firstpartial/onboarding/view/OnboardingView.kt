package com.jorgeromo.androidClassMp1.firstpartial.onboarding.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch

// TAG para logs
private const val TAG = "OnboardingView"

@Composable
fun OnboardingView(
    viewModel: OnboardingViewModel,
    onFinish: () -> Unit = {} // callback
) {
    val pages by viewModel.pages.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    val context = LocalContext.current

    // Log para seguimiento del ciclo de vida
    Log.d(TAG, "OnboardingView composable iniciado")
    Log.i(TAG, "Número de páginas: ${pages.size}, Página actual: $currentPage")

    val pagerState = rememberPagerState(
        initialPage = currentPage,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

    // Sync VM <-> Pager
    LaunchedEffect(pagerState.currentPage) {
        Log.v(TAG, "Página cambiada en pager: ${pagerState.currentPage}")
        viewModel.setPage(pagerState.currentPage)
    }
    LaunchedEffect(currentPage) {
        if (pagerState.currentPage != currentPage) {
            Log.d(TAG, "Sincronizando página del VM con pager: $currentPage")
            pagerState.scrollToPage(currentPage)
        }
    }

    Scaffold(
        bottomBar = {
            BottomBarView(
                isLastPage = viewModel.isLastPage(),
                page = currentPage,
                total = pages.size,
                onPrev = {
                    Log.d(TAG, "Botón Anterior presionado")
                    if (currentPage > 0) {
                        scope.launch {
                            Log.v(TAG, "Navegando a página anterior: ${currentPage - 1}")
                            pagerState.animateScrollToPage(currentPage - 1)
                        }
                    } else {
                        Log.w(TAG, "Intento de navegar a página anterior cuando ya está en la primera")
                    }
                },
                onNext = {
                    Log.d(TAG, "Botón Siguiente/Empezar presionado")
                    if (!viewModel.isLastPage()) {
                        scope.launch {
                            Log.v(TAG, "Navegando a página siguiente: ${currentPage + 1}")
                            pagerState.animateScrollToPage(currentPage + 1)
                        }
                    } else {
                        // Punto de debugging recomendado: coloca un breakpoint aquí
                        Log.i(TAG, "Onboarding completado - llamando callback")

                        // Toast para el usuario
                        Toast.makeText(context, "¡Onboarding completado!", Toast.LENGTH_SHORT).show()

                        onFinish() // <- callback
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                Log.v(TAG, "Renderizando página: $page - ${pages[page].title}")
                OnboardingPageView(pageModel = pages[page])
            }

            DotsIndicatorView(
                totalDots = pages.size,
                selectedIndex = currentPage,
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 16.dp)
            )
        }
    }
}