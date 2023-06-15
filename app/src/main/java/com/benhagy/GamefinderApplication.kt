package com.benhagy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// generic application class needed by dagger hilt for dependency injection

@HiltAndroidApp
class GamefinderApplication: Application()