package com.example.suaveslomitos.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.suaveslomitos.ui.theme.Graphite
import com.example.suaveslomitos.ui.theme.Titanium

// TODO: Customizable sizes or declared as theme params

@Composable
internal fun <T> DrawBigImageList(list: List<BigImageItem<T>>, placeHolderId: Int? = null) {
    BigImageListView(listItems = list, placeHolderId)
}

@Preview
@Composable
internal fun DrawShimmerBigImageList() {
    LazyColumn {
        items(5) {
            BigImageShimmerView()
        }
    }
}


@Composable
private fun <T> BigImageListView(listItems: List<BigImageItem<T>>, placeHolderId: Int?) {
    LazyColumn {
        items(listItems) { item ->
            BigImageItemView(item, placeHolderId)
        }
    }
}

@Composable
private fun <T> BigImageItemView(item: BigImageItem<T>, placeHolderId: Int?) {
    val imageWidth = 130
    val columStartPadding = 10
    val descriptionStartPadding = imageWidth - columStartPadding

    var isLoading by rememberSaveable { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = descriptionStartPadding.dp)
                .height(150.dp)
                .align(Alignment.BottomEnd)
                .clip(shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                .background(Color.White)
                .padding(start = 12.dp, top = 8.dp, bottom = 16.dp, end = 8.dp)
                .padding(start = columStartPadding.dp)
        ) {
            Text(
                text = item.title, color = Graphite, fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = item.description,
                color = Titanium,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, bottom = 8.dp)

            )
            Text(
                text = item.footNote, color = Graphite,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(imageWidth.dp)
                .clip(shape = RoundedCornerShape(10.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = item.imageContentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                onSuccess = { isLoading = false }
            )
            if (placeHolderId != null && isLoading) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    painter = painterResource(id = placeHolderId),
                    contentDescription = "Placeholder",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Preview
@Composable
private fun BigImageShimmerView() {
    val imageWidth = 130
    val columStartPadding = 10
    val descriptionStartPadding = imageWidth - columStartPadding

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = descriptionStartPadding.dp)
                .height(150.dp)
                .align(Alignment.BottomEnd)
                .padding(start = 12.dp, top = 8.dp, bottom = 16.dp, end = 8.dp)
                .padding(start = columStartPadding.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(shimmerBrush(targetValue = 1000f))
            )
            Box(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(3.dp))
                    .background(shimmerBrush(targetValue = 1000f))

            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(shimmerBrush(targetValue = 1000f))
            )
        }
        Box(
            modifier = Modifier
                .width(imageWidth.dp)
                .fillMaxHeight()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(shimmerBrush(targetValue = 1000f))
        )
    }
}

internal data class BigImageItem<T>(
    val imageUrl: String,
    val imageContentDescription: String = "",
    val title: String,
    val description: String,
    val footNote: String = "",
    val item: T
)

