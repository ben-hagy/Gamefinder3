package com.benhagy.gamefinder3.presentation.favorites_screen.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.benhagy.gamefinder3.domain.usecases.UseCaseContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    useCaseContainer: UseCaseContainer
): ViewModel() {
    var state = mutableStateOf(FavoritesScreenState())
}