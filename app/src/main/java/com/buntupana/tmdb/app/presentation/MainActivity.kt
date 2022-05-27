package com.buntupana.tmdb.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.presentation.theme.TMDBTheme
import com.buntupana.tmdb.feature.discover.presentation.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

//@RootNavGraph(start = true)
//@Destination
//@Composable
//fun Greeting(
//    navigator: DestinationsNavigator,
//    discoverViewModel: DiscoverViewModel = hiltViewModel()
//) {
//    DiscoverScreenDestination
////    navigator.navigate(DiscoverScreen)
//    Text(text = "Hello App Module")
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TMDBTheme {
//        Greeting("Android")
    }
}