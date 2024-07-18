package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.states.status.ProduceType
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor

@Composable
fun ProduceItemCardComponent(
    modifier: Modifier = Modifier,
    painter: Painter,
    produceName: String,
    produceType: ProduceType,
    produceQuantity: String,
    producerName: String,
    produceDate: String,
    onItemClick: () -> Unit
) {
    ElevatedCard(
        onClick = onItemClick,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(8.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryColor)
    ) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Image(
                painter = painter,
                contentDescription = "item image",
                modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .height(180.dp)
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier.weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = produceName, style = MaterialTheme.typography.headlineLarge.copy(color = PrimaryTextColor, fontWeight = FontWeight.Bold), textAlign = TextAlign.Center)
                Spacer(modifier = modifier.height(8.dp))
                Text(text = produceType.displayName, style = MaterialTheme.typography.titleMedium.copy(color = PrimaryTextColor), textAlign = TextAlign.Center)
                Spacer(modifier = modifier.height(8.dp))
                Text(text = "Quantity: $produceQuantity", style = MaterialTheme.typography.titleMedium.copy(color = PrimaryTextColor), textAlign = TextAlign.Center)
                Spacer(modifier = modifier.height(8.dp))
                Text(text = "Producer: $producerName", style = MaterialTheme.typography.titleMedium.copy(color = SecondaryTextColor), textAlign = TextAlign.Center)
                Spacer(modifier = modifier.height(8.dp))
                Text(text = "Uploaded on $produceDate", style = MaterialTheme.typography.titleMedium.copy(color = SecondaryColor), textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
private fun ProduceItemCardComponentPreview() {
    ProduceItemCardComponent(painter = painterResource(id = R.drawable.ic_andy), produceName = "Son of a bitch", produceType = ProduceType.Vegetable, produceQuantity = "2", producerName = "Andreas Weisberg", produceDate = "2023-06-01", onItemClick = {})
}