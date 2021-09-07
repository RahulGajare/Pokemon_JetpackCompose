package com.rg.pokemon.ui.utils

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
    fun CircularLoader(color: Color, modifier: Modifier) {
        CircularProgressIndicator(color = color,
        modifier = modifier)
    }
