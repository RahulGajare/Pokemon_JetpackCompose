package com.rg.pokemon.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rg.pokemon.R

@Composable
fun PokemonListScreen() {
    var searchText by remember {
        mutableStateOf("")
    }
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_internation_pok_mon_logo),
                contentDescription = "pokemon Logo",
                modifier = Modifier.fillMaxWidth()
            )
            SearchBar(hint = "Search...", searchText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                searchText = it
            }
        }
    }

}

@Composable
fun SearchBar(hint: String, searchText: String, modifier : Modifier ,onValueChange: (String) -> Unit) {

Box(modifier = modifier) {
    TextField(
    maxLines = 1,
    singleLine = true,
    modifier = Modifier
        .fillMaxWidth()
        .clip(AbsoluteRoundedCornerShape(20.dp))
        .shadow(5.dp, RoundedCornerShape(20.dp))
        .background(Color.White, CircleShape),

    value = searchText,
    onValueChange = {
        onValueChange(it)
    },
    placeholder = { Text(text = hint , color = Color.Gray) },
    colors = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )


)
}





}

@Preview
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreen()
}