package com.stu74526.project_74526

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.stu74526.project_74526.ui.theme.RoyalBlue

var allProducts by mutableStateOf(emptyMap<String, Product>())

var productsCart = mutableMapOf<String, Int>()

@Composable
fun DisplayImageFromUrl(url: String, modifier: Modifier = Modifier) {
    val painter = rememberImagePainter(data = url)

    Image(
        painter = painter,
        contentDescription = "This person doesn't exist",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ShowImage(drawable: Int, modifier: Modifier = Modifier) // Show image
{
    Image(
        painter = painterResource(id = drawable),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun ShowImageString(drawable: String, modifier: Modifier = Modifier) // Show image
{
    Image(
        painter = painterResource(
            id = LocalContext.current.resources.getIdentifier(
                drawable,
                "drawable", "com.stu74526.project_74526"
            )
        ),
        contentDescription = null,
        modifier = modifier
    )
}


@Composable
fun ShowImageScale(
    draw: Int, modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    Image(
        painter = painterResource(id = draw),
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}


@Composable
fun BottomBarGlobal(
    home: () -> Unit = {},
    historic: () -> Unit = {},
    cart: () -> Unit = {},
    profile: () -> Unit = {}
) {
    val productsCart by remember { mutableStateOf(productsCart) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(RoyalBlue)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        BottomBar(
            cart = cart,
            home = home,
            historic = historic,
            profile = profile,
            productCart = productsCart.size
        )
    }
}

@Composable
fun BottomBar(
    home: () -> Unit = {},
    historic: () -> Unit = {},
    cart: () -> Unit = {},
    profile: () -> Unit = {}, productCart: Int
) {
    ShowImage(drawable = R.drawable.home, modifier = Modifier
        .size(50.dp)
        .clickable { home() })
    ShowImage(drawable = R.drawable.historical, modifier = Modifier
        .size(50.dp)
        .clickable { historic() })
    if (productCart > 0) {
        Box {
            ShowImage(drawable = R.drawable.cart, modifier = Modifier
                .size(50.dp)
                .clickable { cart() })
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.TopEnd)
                    .width(15.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = productCart.toString(),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        ShowImage(drawable = R.drawable.cart, modifier = Modifier
            .size(50.dp)
            .clickable { cart() })
    }
    ShowImage(drawable = R.drawable.profile, modifier = Modifier
        .size(50.dp)
        .clickable { profile() })
}
