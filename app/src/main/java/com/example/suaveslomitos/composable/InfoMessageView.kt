package com.example.suaveslomitos.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawInfoMessage(infoImage: Painter, message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = infoImage,
                contentDescription = message,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )
        }
    }
}