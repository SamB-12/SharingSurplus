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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryColor
import com.example.sharingsurplus.presentation.ui.theme.SecondaryTextColor

@Composable
fun CommunityFormPostComponent(
    modifier: Modifier = Modifier,
    postTitle: String = "Post Title",
    postDescription: String = "Post Description",
    postedBy: String = "Posted By",
) {
    ElevatedCard(
        //onClick = onItemClick,
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
                .padding(horizontal = 16.dp, vertical = 8.dp),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = postTitle, style = MaterialTheme.typography.headlineLarge.copy(color = PrimaryTextColor, fontWeight = FontWeight.Bold), textAlign = TextAlign.Center)
            Spacer(modifier = modifier.height(8.dp))
            Text(text = postDescription, style = MaterialTheme.typography.titleMedium.copy(color = PrimaryTextColor), textAlign = TextAlign.Center)
            Spacer(modifier = modifier.height(8.dp))
            Text(text = "Posted By: $postedBy", style = MaterialTheme.typography.titleMedium.copy(color = SecondaryTextColor), textAlign = TextAlign.Center)
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
private fun CommunityFormPostComponentPreview() {
    CommunityFormPostComponent()
}