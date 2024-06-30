package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.presentation.ui.components.AddProduceImageComponent
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.DropDownMenuComponent
import com.example.sharingsurplus.presentation.ui.components.QuantitySelectorComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor

@Composable
fun AddScreenMinor(
    modifier: Modifier = Modifier,
    buttonOnClick: () -> Unit = {}
) {//add a view model as well
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = PrimaryColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Text(text = "Add a product", style = MaterialTheme.typography.headlineLarge)// replace with the top bar
        //Spacer(modifier = modifier.height(16.dp))
        Text(text = "Enter Produce Name", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        TextFieldComponent(label = "Product Name")
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Give a description", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        TextFieldComponent(label = "Product Description")
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Select the type of produce", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        //TextFieldComponent(label = "Product Type")//This should be a dropdown/spinner
        DropDownMenuComponent(label = "Product Type", produceTypes = listOf("Vegetables", "Fruits", "Dairy"))
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Enter the quanity of produce", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        QuantitySelectorComponent(label1 = "Quantity", label2 = "Unit") //label 2 should be a spinner
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Select the Location", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        TextFieldComponent(label = "Location")//should ask the user for a normal location or an anonymous location
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Enter the pickup Instructions", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        TextFieldComponent(label = "Pickup Instructions")
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Enter the last pickup date", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        TextFieldComponent(label = "PickUp Date")
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Insert the Image", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        //TextFieldComponent(label = "Image") // add the image logo and do it!
        AddProduceImageComponent(painter = painterResource(id = R.drawable.add_screen_image_placeholder))
        Spacer(modifier = modifier.height(16.dp))
        ButtonComponent(text = "Add Product", onClick = {buttonOnClick()})
    }
}

@Preview(showBackground = true)
@Composable
private fun AddScreenMinorPreview() {
    AddScreenMinor()
}