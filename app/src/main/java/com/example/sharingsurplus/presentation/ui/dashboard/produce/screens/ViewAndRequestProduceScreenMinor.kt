package com.example.sharingsurplus.presentation.ui.dashboard.produce.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sharingsurplus.R
import com.example.sharingsurplus.data.states.dashboard.produce.ProduceType
import com.example.sharingsurplus.presentation.ui.components.ButtonComponent
import com.example.sharingsurplus.presentation.ui.components.CalendarWithEndDateComponent
import com.example.sharingsurplus.presentation.ui.components.ProfileInfoDetailsComponent
import com.example.sharingsurplus.presentation.ui.components.TextFieldComponent
import com.example.sharingsurplus.presentation.ui.components.TimePicker2
import com.example.sharingsurplus.presentation.ui.components.UrlOpenerComponent
import com.example.sharingsurplus.presentation.ui.theme.PrimaryColor
import com.example.sharingsurplus.presentation.ui.theme.PrimaryTextColor

@Composable
fun ViewAndRequestProduceScreenMinor(
    modifier: Modifier = Modifier,
    painter: Painter,
    produceName: String = "",
    producerName: String = "",
    produceDescription: String = "",
    produceType: ProduceType? = ProduceType.None,
    produceQuantity: Int = 0,
    produceUnit: String = "",
    produceBestBeforeDate: String = "",
    producePickupInstructions: String = "",
    produceLocation: String = "",
    pickupDate: String = "",
    pickupTime: String = "",
    requirements: String = "",
    isDatePickerVisible: Boolean = false,
    isTimePickerVisible: Boolean = false,
    onDatePickerVisible: (Boolean) -> Unit = {},
    onTimePickerVisible: (Boolean) -> Unit = {},
    onPickUpDateSelected: (String) -> Unit = {},
    onPickUpTimeSelected: (String) -> Unit = {},
    onRequirementsChanged: (String) -> Unit = {},
    onProduceRequested: () -> Unit = {},
    onProduceLocationClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        //Icon(imageVector = Icons.Rounded.Person, contentDescription = "Person Icon", modifier = modifier.size(180.dp))
        Image(
            painter = painter,
            contentDescription = "Image of the produce",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(200.dp),
                //.weight(1f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = produceName, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = PrimaryTextColor)
        Spacer(modifier = Modifier.height(32.dp))
        //Text(text = "View your profile", style = MaterialTheme.typography.bodyMedium, color = PrimaryTextColor)
        ProfileInfoDetailsComponent(text1 = "Name", text2 = produceName)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Description", text2 = produceDescription)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Produce Type", text2 = produceType?.name ?: "None")
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Quantity", text2 = "$produceQuantity $produceUnit")
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Pickup Instructions", text2 = producePickupInstructions)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Best Before", text2 = produceBestBeforeDate)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        ProfileInfoDetailsComponent(text1 = "Uploaded By", text2 = producerName)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        //ProfileInfoDetailsComponent(text1 = "Address", text2 = produceLocation)//TODO:make this clickable and implement map
        UrlOpenerComponent(text1 = "Address", text2 = produceLocation, onUrlClicked = onProduceLocationClicked)
        Spacer(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(1.dp)
            .background(color = Color.Gray)
            .fillMaxWidth()
        )
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Select the Pickup Date", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        //Add Date Picker here
        CalendarWithEndDateComponent(
            bestBeforeDate = pickupDate,
            maxDate = produceBestBeforeDate,
            onBestBeforeDateChanged = onPickUpDateSelected,
            isDatePickerDialogVisible = isDatePickerVisible,
            datePickerDialogChanged = onDatePickerVisible
        ) //pass max date
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Select the Pickup Time", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        //Add Time Picker here
        TimePicker2(
            timeSelected = pickupTime,
            onTimeSelectedChanged = onPickUpTimeSelected,
            isTimePickerDialogVisible = isTimePickerVisible,
            timePickerDialogChanged = onTimePickerVisible
        )//Refactor it
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Add Requirements", style = MaterialTheme.typography.labelLarge, modifier = modifier
            .align(Alignment.Start)
            .padding(horizontal = 16.dp))
        //Add requirements here
        TextFieldComponent(label = "Requirements", value = requirements, onValueChanged = onRequirementsChanged)
        Spacer(modifier = Modifier.height(40.dp))
        ButtonComponent(onClick = onProduceRequested, text = "Request Produce")
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Preview
@Composable
private fun ViewAndRequestProduceScreenMinorPreview() {
    ViewAndRequestProduceScreenMinor(
        painter = painterResource(id = R.drawable.ic_andy),
        produceName = "Produce Name",
        producerName = "Producer Name",
        produceDescription = "Produce Description",
        produceType = ProduceType.None,
        produceQuantity = 1,
        produceUnit = "kg",
        produceBestBeforeDate = "Best Before Date",
        producePickupInstructions = "Pickup Instructions",
        produceLocation = "Address"
    )
}