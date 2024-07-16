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
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor

@Composable
fun RequestSentItemCardComponent(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.ic_andy),
    produceName: String = "Produce Name",
    requestedQuantity: String = "1",
    requestedDate: String = "12/08/2001",
    requestedTime: String = "02:23",
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(8.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Image(
                    painter = painter,
                    contentDescription = "request image",
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
                    Text(text = "Requested Quantity: $requestedQuantity", style = MaterialTheme.typography.titleMedium.copy(color = PrimaryTextColor), textAlign = TextAlign.Center)
                    Spacer(modifier = modifier.height(8.dp))
                    Text(text = "Requested Date: $requestedDate", style = MaterialTheme.typography.titleMedium.copy(color = SecondaryTextColor), textAlign = TextAlign.Center)
                    Spacer(modifier = modifier.height(8.dp))
                    Text(text = "Requested Time:  $requestedTime", style = MaterialTheme.typography.titleMedium.copy(color = SecondaryTextColor), textAlign = TextAlign.Center)
                }
            }
            DoubleButtonComponents(label1 = "Edit", label2 = "Delete", onYesClick = onEdit, onNoClick = onDelete)
            Spacer(modifier = modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun RequestSentItemCardComponentPreview() {
    RequestSentItemCardComponent()
}