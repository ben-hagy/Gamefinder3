package com.benhagy.gamefinder3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.benhagy.gamefinder3.presentation.bottom_nav_bar.BottomNavigationBar
import com.benhagy.gamefinder3.presentation.ui.theme.Gamefinder3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gamefinder3Theme {
                BottomNavigationBar()
            }
        }
    }
}
