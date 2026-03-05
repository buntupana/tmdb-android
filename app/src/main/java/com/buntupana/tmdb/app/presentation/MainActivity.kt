package com.buntupana.tmdb.app.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.buntupana.tmdb.app.presentation.nav3.NavigationRootNav3
import com.buntupana.tmdb.core.ui.navigation.NavRoutesMain
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.ObserveAsEvents
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {

    private val navRoutesMain: NavRoutesMain by inject()
    private val urlProvider: UrlProvider by inject()

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                viewModel.reloadSession.collectAsStateWithLifecycle()
                val context = LocalContext.current

                val navController = rememberNavController()
                navRoutesMain.init(navController)

                val snackbarHostState = remember { SnackbarHostState() }

                val scope = rememberCoroutineScope()

                ObserveAsEvents(
                    SnackbarController.events,
                    snackbarHostState
                ) { event ->
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
                            actionLabel = event.action?.name,
                            duration = event.snackbarDuration
                        )

                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    NavigationRootNav3()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        Timber.d("onNewIntent() called with: intent = [$intent]")
        super.onNewIntent(intent)
        navRoutesMain.handleDeepLink(intent)
    }
}
