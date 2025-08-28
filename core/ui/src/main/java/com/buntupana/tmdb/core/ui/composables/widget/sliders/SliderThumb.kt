package com.buntupana.tmdb.core.ui.composables.widget.sliders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.SliderThumbColor

@Composable
fun SliderThumb(
    modifier: Modifier = Modifier,
    value: String
) {
    Column(
        modifier = modifier.height(120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp), // Reduced vertical padding
                text = value,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Box(
            modifier = Modifier
                .background(SliderThumbColor, shape = RoundedCornerShape(15.dp))
                .size(width = 40.dp, height = 30.dp)
        )

        Box(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun SliderThumbPreview() {
    SliderThumb(
        value = "20"
    )
}