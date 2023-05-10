package com.benhagy.gamefinder3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.benhagy.gamefinder3.presentation.NavGraphs
import com.benhagy.gamefinder3.presentation.ui.theme.Gamefinder3Theme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gamefinder3Theme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Gamefinder3Theme {
//        Greeting("Android")
//    }
//}