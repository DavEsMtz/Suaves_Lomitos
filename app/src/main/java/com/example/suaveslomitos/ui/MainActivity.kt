package com.example.suaveslomitos.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.suaveslomitos.composable.DrawTopBar
import com.example.suaveslomitos.ui.list.DogsListScreen
import com.example.suaveslomitos.ui.list.DogsListViewModel
import com.example.suaveslomitos.ui.theme.BoneMeal
import com.example.suaveslomitos.ui.theme.SuavesLomitosTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: DogsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
        setContent {
            SuavesLomitosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BoneMeal
                ) {
                    Scaffold(
                        topBar = {
                            DrawTopBar(
                                refresh = viewModel::populateDogs
                            )
                        }
                    ) { paddingValues ->
                        DogsListScreen(viewModel = viewModel, paddingValues = paddingValues)
                    }
                }
            }
        }
    }
}