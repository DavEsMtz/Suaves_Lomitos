package com.example.suaveslomitos.ui.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.suaveslomitos.R
import com.example.suaveslomitos.composable.BigImageItem
import com.example.suaveslomitos.composable.DrawBigImageList
import com.example.suaveslomitos.composable.DrawShimmerBigImageList
import com.example.suaveslomitos.composable.DrawInfoMessage

@Composable
fun DogsListScreen(viewModel: DogsListViewModel, paddingValues: PaddingValues = PaddingValues()) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        when (val viewState = viewModel.viewState.collectAsState().value) {
            DogsListViewState.DogsListError -> {
                DrawInfoMessage(
                    infoImage = painterResource(id = R.drawable.ic_baseline_failure),
                    message = stringResource(id = R.string.label_generic_error)
                )
            }

            DogsListViewState.EmptyDogsList -> {
                DrawInfoMessage(
                    infoImage = painterResource(id = R.drawable.icon_lomito),
                    message = stringResource(id = R.string.label_empty_search)
                )
            }

            DogsListViewState.LoadingDogs -> {
                DrawShimmerBigImageList()
            }

            is DogsListViewState.SuccessDogs -> {
                val bigImageItems = viewState.dogs.map { dog ->
                    BigImageItem(
                        imageUrl = dog.image,
                        title = dog.dogName,
                        description = dog.description,
                        footNote = stringResource(id = R.string.label_age_description, dog.age),
                        item = dog
                    )
                }
                DrawBigImageList(list = bigImageItems)
            }
        }
    }
}

