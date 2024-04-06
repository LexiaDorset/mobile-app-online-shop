package com.stu74526.project_74526

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

@Composable
fun DisplayImageFromUrl(url: String) {
    val painter = rememberImagePainter(data = url)

    Image(
        painter = painter,
        contentDescription = "Image from URL",
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun HomePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar()
        SearchBar()
        Category()
    }
}

@Composable
fun AppBar()
{
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        Text(
            text = "Online Shopping App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        ShowImage(drawable = R.drawable.cart, modifier = Modifier.size(50.dp))
        DisplayImageFromUrl("https://thispersondoesnotexist.com/")
    }
}

@Composable
fun SearchBar()
{
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Box(
        Modifier
            .border(1.dp, Color.DarkGray, CircleShape)
            .fillMaxWidth(0.8f),
        contentAlignment = Alignment.CenterStart)
    {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {

            BasicTextField(value = text, onValueChange = {
                if (it.text.length <= 35) {
                text = it
            } },
                modifier = Modifier
                    .height(30.dp)
                    .padding(5.dp)
                    .weight(1f),
                )
            ShowImage(drawable =R.drawable.search, modifier = Modifier
                .size(30.dp)
                .padding(5.dp, 5.dp, 10.dp, 5.dp))
        }
    }
}

@Composable
fun Category() {
    var categories by remember { mutableStateOf(emptyMap<String, String>()) }

    LaunchedEffect(Unit) {
        categories = getCategories()
    }

    Row {
        categories.forEach { (key, value) ->
            Button(onClick = { /* handle button click */ }) {
                Text(text = value)
            }
        }
    }
}

suspend fun getCategories(): Map<String, String> {
    val categories = mutableMapOf<String, String>()

    categoryCollection.get().addOnSuccessListener { documents ->
        for (document in documents) {
            categories[document.id] = document.data[categoryName].toString()
        }
    }.await()

    return categories
}
