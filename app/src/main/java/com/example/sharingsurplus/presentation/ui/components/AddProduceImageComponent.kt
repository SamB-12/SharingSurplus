package com.example.sharingsurplus.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun AddProduceImageComponent(
    modifier: Modifier = Modifier,
    painter: Painter,//comes from the vm
    onAddImageClicked: () -> Unit = {}, // also from the vm
    isImagePickerDialogVisible: Boolean = false,
    onImagePickerDialogChange: (Boolean) -> Unit = {},
    onGalleryClicked: () -> Unit = {},
    onCameraClicked: () -> Unit = {},
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(color = PrimaryColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Image(
            painter = painter,
            contentDescription = "Image of the produce",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(200.dp)
                .weight(1f),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Add Image",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    if (isImagePickerDialogVisible) {
                        onImagePickerDialogChange(false)
                    } else {
                        onImagePickerDialogChange(true)
                    }
                }
        )
    }
    
    if (isImagePickerDialogVisible) {
        MultipleOptionPickerDialogComponent(onDismissRequest = { onImagePickerDialogChange(false) }, option1 = "Select photo from Gallery", option2 = "Take photo", onOption1Click = onGalleryClicked, onOption2Click = onCameraClicked)
    }
}

@Preview
@Composable
private fun AddProduceImageComponentPreview() {
    AddProduceImageComponent(painter = painterResource(id = R.drawable.add_screen_image_placeholder))
}